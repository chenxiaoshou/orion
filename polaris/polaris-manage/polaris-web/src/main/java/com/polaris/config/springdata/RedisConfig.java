package com.polaris.config.springdata;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.polaris.common.constant.PatternConstants;
import com.polaris.common.constant.RedisConstants;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Spring-data-redis
 */
@Configuration
@EnableRedisRepositories
@PropertySource("classpath:config.properties")
public class RedisConfig {

	private static final Logger LOGGER = LogManager.getLogger(RedisConfig.class);
	
	@Autowired
	private Environment env;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(env.getRequiredProperty("redis.maxTotal", Integer.class));
		poolConfig.setMaxIdle(env.getRequiredProperty("redis.maxIdle", Integer.class));
		poolConfig.setMaxWaitMillis(env.getRequiredProperty("redis.maxWaitMillis", Long.class));
		poolConfig.setTestOnBorrow(env.getRequiredProperty("redis.testOnBorrow", Boolean.class));
		return poolConfig;
	}

	/**
	 * 配置redis集群
	 * 
	 * @return
	 */
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration() {
		String[] hostPortArr = env.getProperty("redis.host_ports").split(",");
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(Arrays.asList(hostPortArr));
		clusterConfiguration.setMaxRedirects(env.getRequiredProperty("redis.maxRedirects", Integer.class));
		return clusterConfiguration;
	}

	/**
	 * 配置Redis连接工厂
	 * 
	 * @return
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration(),
				jedisPoolConfig());
		jedisConnectionFactory.setUsePool(true);
		jedisConnectionFactory.setTimeout(30000);
		jedisConnectionFactory.afterPropertiesSet();
		testConn(jedisConnectionFactory);
		return jedisConnectionFactory;
	}
	
	// 测试连接，有问题就抛错, 通知容器中止启动
	private void testConn(JedisConnectionFactory jedisConnectionFactory) {
		RedisClusterConnection conn = null;
		try {
			conn = jedisConnectionFactory.getClusterConnection(); 
			if (!RedisConstants.PONG.equalsIgnoreCase(conn.ping())) {
				LOGGER.fatal("Redis服务器连接异常");
				throw new RuntimeException("Redis服务器连接异常");
			}
		} catch (Exception e) {
			LOGGER.fatal("Redis服务器连接异常");
			throw new RuntimeException("Redis服务器连接异常", e);
		} finally {
			if(conn != null) {
				conn.close();
			}
		}
	}
	

	/**
	 * 配置Spring-data的redisTemplate
	 * 
	 * @return
	 */
	@Bean
	public RedisTemplate<Serializable, Serializable> redisTemplate() {
		RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
		return redisTemplate;
	}

	/**
	 * 配置stringRedisTemplate
	 * 
	 * @return
	 */
	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		stringRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
		return stringRedisTemplate;
	}

	/**
	 * Jackson提供的对象<->Json互相转换的工具类
	 * 
	 * @return
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		objectMapper.setDateFormat(new SimpleDateFormat(PatternConstants.DATE_FORMAT_PATTERN_1));
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		objectMapper.registerModule(module);
		return objectMapper;
	}

	@Bean
	public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper());
		return jackson2JsonRedisSerializer;
	}
	
}
