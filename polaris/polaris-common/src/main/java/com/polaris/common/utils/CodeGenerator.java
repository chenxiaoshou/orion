package com.polaris.common.utils;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import com.polaris.common.constant.PatternConstants;
import com.polaris.common.exception.PolarisException;

public final class CodeGenerator {

	private final static Logger LOGGER = LogManager.getLogger(CodeGenerator.class);

	private CodeGenerator() {

	}

	private static long getNextSeqFromRedis(String seqKey) {
		// 每天都从1开始获取新的SEQ
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong(seqKey,
				(RedisConnectionFactory) SpringUtil.getBean("jedisConnectionFactory"));
		long seqNum = redisAtomicLong.incrementAndGet();
		if (seqNum == 1) { // 当第一次取出索引的时候，设置过期时间
			redisAtomicLong.expire(1, TimeUnit.HOURS); // 1小时之后过期
		}
		LOGGER.debug("Obtain seqNum from redis. seqKey [" + seqKey + "] seqNum [" + seqNum + "]");
		return seqNum;
	}

	// TODO 需要严格测试
	public synchronized static String generateUniqueStringCode(String perfix, int length) {
		if (length < 24) {
			throw new PolarisException("必须保证最小字符的长度 >= 24");
		}
		String timestamp = DateUtil.date2str(DateUtil.now(), PatternConstants.DATE_FORMAT_PATTERN_7);
		String seqKey = StringUtil.joinWith("_", perfix, timestamp);
		long seqNum = getNextSeqFromRedis(seqKey);
		int zeroLength = length - perfix.length() - timestamp.length() - String.valueOf(seqNum).length();
		StringBuilder buf = new StringBuilder();
		buf.append(perfix);
		buf.append(timestamp);
		for (int i = 0; i < zeroLength; i++) {
			buf.append("0");
		}
		buf.append(seqNum);
		return buf.toString();
	}

	public synchronized static String generateUniqueIntCode(int length) {
		// TODO
		return null;
	}

}
