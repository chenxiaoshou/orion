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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.polaris.common.constant.PatternConstants;

@Component
public class IdGenerator implements Configurable, IdentifierGenerator {

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	private int idLength;

	private String perfix;
	
	private String timestamp;

	public long getNextSeqFromRedis() {
		// 每天都从1开始获取新的SEQ
		timestamp = DateUtil.date2str(new Date(), PatternConstants.DATE_FORMAT_PATTERN_4);
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong(timestamp, jedisConnectionFactory);
		redisAtomicLong.expire(1, TimeUnit.DAYS); // 1天之后过期
		return redisAtomicLong.incrementAndGet();
	}

	@Override
	public synchronized Serializable generate(SharedSessionContractImplementor session, Object obj)
			throws HibernateException {
		long seqNum = getNextSeqFromRedis();
		// TODO 在seqnum前面补零之后拼接
		
		return perfix + timestamp + seqNum;
	}

	@Override
	public void configure(Type type, Properties params, ServiceRegistry arg2) throws MappingException {
		this.idLength = Integer.parseInt(params.getProperty("idLength"));
		this.perfix = params.getProperty("perfix");
	}

}
