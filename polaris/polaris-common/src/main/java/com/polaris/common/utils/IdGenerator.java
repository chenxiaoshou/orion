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
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import com.polaris.common.constant.PatternConstants;

import redis.clients.jedis.JedisPoolConfig;

public class IdGenerator implements Configurable, IdentifierGenerator {

	private int idLength;

	private String perfix;

	private String timestamp;

	public long getNextSeqFromRedis() {
		// 每天都从1开始获取新的SEQ
		timestamp = DateUtil.date2str(new Date(), PatternConstants.DATE_FORMAT_PATTERN_4);
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong(timestamp,
				(RedisConnectionFactory) BeanUtil.getBean("jedisConnectionFactory"));
		redisAtomicLong.expire(1, TimeUnit.DAYS); // 1天之后过期
		return redisAtomicLong.incrementAndGet();
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

	// for testing
	public static RedisClusterConfiguration redisClusterConfiguration() {
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
		clusterConfiguration.addClusterNode(new RedisNode("192.168.1.115", 6379));
		clusterConfiguration.addClusterNode(new RedisNode("192.168.1.115", 6380));
		clusterConfiguration.addClusterNode(new RedisNode("192.168.1.115", 6381));
		clusterConfiguration.addClusterNode(new RedisNode("192.168.1.115", 6382));
		clusterConfiguration.addClusterNode(new RedisNode("192.168.1.115", 6383));
		clusterConfiguration.addClusterNode(new RedisNode("192.168.1.115", 6384));
		return clusterConfiguration;
	}

	// for testing
	public static JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration(),
				jedisPoolConfig());
		jedisConnectionFactory.setUsePool(true);
		jedisConnectionFactory.setTimeout(0);
		jedisConnectionFactory.afterPropertiesSet();
		return jedisConnectionFactory;
	}

	// for testing
	public static JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(10);
		poolConfig.setMaxIdle(5);
		poolConfig.setMaxWaitMillis(30000);
		poolConfig.setTestOnBorrow(true);
		return poolConfig;
	}
	
	// for testing
	public static void main(String[] args) {
		String timestamp = DateUtil.date2str(new Date(), PatternConstants.DATE_FORMAT_PATTERN_4);
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong(timestamp, jedisConnectionFactory());
		redisAtomicLong.expire(1, TimeUnit.DAYS);
		for (int i = 0; i < 10; i++) {
			System.out.println(redisAtomicLong.incrementAndGet());
		}
	}

}
