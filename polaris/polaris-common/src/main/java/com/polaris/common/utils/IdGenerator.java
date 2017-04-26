package com.polaris.common.utils;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private final static Logger LOGGER = LogManager.getLogger(IdGenerator.class);

	private int idLength;

	private String perfix;

	private String timestamp;

	private long getNextSeqFromRedis() {
		// 每天都从1开始获取新的SEQ
		String idKey = StringUtil.joinWith("_", perfix, timestamp);
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong(idKey,
				(RedisConnectionFactory) BeanUtil.getBean("jedisConnectionFactory"));
		long seqNum = redisAtomicLong.incrementAndGet();
		if (seqNum == 1) { // 当第一次取出索引的时候，设置过期时间
			redisAtomicLong.expire(1, TimeUnit.DAYS); // 1天之后过期
		}
		LOGGER.debug("Obtain seqNum from redis [" + seqNum + "]");
		return seqNum;
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
		this.timestamp = DateUtil.date2str(DateUtil.now(), PatternConstants.DATE_FORMAT_PATTERN_4);
	}

	// for testing
	/*
	 * public static RedisClusterConfiguration redisClusterConfiguration() {
	 * RedisClusterConfiguration clusterConfiguration = new
	 * RedisClusterConfiguration(Arrays.asList( "192.168.199.105:6379",
	 * "192.168.199.105:6380", "192.168.199.105:6381", "192.168.199.105:6382",
	 * "192.168.199.105:6383", "192.168.199.105:6384"));
	 * clusterConfiguration.setMaxRedirects(5); return clusterConfiguration; }
	 * 
	 * // for testing public static JedisConnectionFactory
	 * jedisConnectionFactory() { JedisConnectionFactory jedisConnectionFactory
	 * = new JedisConnectionFactory(redisClusterConfiguration(),
	 * jedisPoolConfig()); jedisConnectionFactory.setUsePool(true);
	 * jedisConnectionFactory.setTimeout(0);
	 * jedisConnectionFactory.afterPropertiesSet(); return
	 * jedisConnectionFactory; }
	 * 
	 * // for testing public static JedisPoolConfig jedisPoolConfig() {
	 * JedisPoolConfig poolConfig = new JedisPoolConfig();
	 * poolConfig.setMaxTotal(10); poolConfig.setMaxIdle(5);
	 * poolConfig.setMaxWaitMillis(30000); poolConfig.setTestOnBorrow(true);
	 * return poolConfig; }
	 * 
	 * // for testing public static void main(String[] args) { String timestamp
	 * = DateUtil.date2str(new Date(), PatternConstants.DATE_FORMAT_PATTERN_4);
	 * RedisAtomicLong redisAtomicLong = new RedisAtomicLong(timestamp,
	 * jedisConnectionFactory()); for (int i = 0; i < 1000; i++) { new
	 * Thread(new Runnable(){
	 * 
	 * @Override public void run() { long id =
	 * redisAtomicLong.incrementAndGet(); if (id == 1) {
	 * redisAtomicLong.expire(30, TimeUnit.SECONDS); } System.out.println(id); }
	 * }).start(); } }
	 */

}
