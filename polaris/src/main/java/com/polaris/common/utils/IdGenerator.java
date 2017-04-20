package com.polaris.common.utils;

import java.util.Arrays;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import redis.clients.jedis.JedisPoolConfig;

public class IdGenerator {

	private IdGenerator() {
	}

	public static Long getNextId() {
		return Nested.genrator.incrementAndGet();
	}

	// 在第一次被引用时被加载
	private static class Nested {
		private static RedisAtomicLong genrator = new RedisAtomicLong("OrderNo", connFactory, 1);
	}

	public static void main(String args[]) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(100);
		poolConfig.setMaxIdle(30);
		poolConfig.setMaxWaitMillis(30000);
		poolConfig.setTestOnBorrow(true); // 借出之前测试是否可用
		RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
		RedisClusterNode.SlotRange slotRange = new RedisClusterNode.SlotRange(Arrays.asList(1));
		RedisNode node1 = new RedisClusterNode("127.0.0.1", 6379, slotRange);
		clusterConfig.addClusterNode(node1);
		JedisConnectionFactory connFactory = new JedisConnectionFactory(clusterConfig, poolConfig);
		Long id = IdGenerator.getNextId();
	}
}
