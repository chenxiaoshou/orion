package com.polaris.common.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import com.polaris.common.constant.PatternConstants;

public class IdGenerator implements Configurable, IdentifierGenerator {

	private int idLength;

	private String perfix;

	private String timestamp;

	public long getNextSeqFromRedis() {
		// 每天都从1开始获取新的SEQ
		timestamp = DateUtil.date2str(new Date(), PatternConstants.DATE_FORMAT_PATTERN_4);
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong(timestamp,
				(RedisConnectionFactory) BeanUtil.getBean("jedisConnectionFactory"));
		long reqNum = redisAtomicLong.incrementAndGet();
		if (reqNum == 1) {
			redisAtomicLong.expire(1, TimeUnit.DAYS); // 1天之后过期
		}
		return reqNum;
	}

	@Override
	public synchronized Serializable generate(SharedSessionContractImplementor session, Object obj)
			throws HibernateException {
		long seqNum = getNextSeqFromRedis();
		int zeroLength = idLength - perfix.length() - timestamp.length() - String.valueOf(seqNum).length();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < zeroLength; i++) {
			buf.append("0");
		}
		return perfix + timestamp + buf + seqNum;
	}

	@Override
	public void configure(Type type, Properties params, ServiceRegistry arg2) throws MappingException {
		this.idLength = Integer.parseInt(params.getProperty("idLength"));
		this.perfix = params.getProperty("perfix");
	}

}
