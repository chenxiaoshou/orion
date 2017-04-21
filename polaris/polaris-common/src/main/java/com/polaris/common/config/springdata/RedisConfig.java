package com.polaris.common.config.springdata;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.polaris.common.constant.PatternConstants;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Spring-data-redis
 */
@Configuration
@PropertySource("classpath:config.properties")
public class RedisConfig {

	@Value("${redis.host_ports}")
	private String hostPorts;

	@Value("${redis.maxTotal}")
	private int maxTotal;

	@Value("${redis.maxIdle}")
	private int maxIdle;

	@Value("${redis.maxWaitMillis}")
	private int maxWaitMillis;

	@Value("${redis.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${redis.maxRedirects}")
	private int maxRedirects;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		poolConfig.setTestOnBorrow(testOnBorrow);
		return poolConfig;
	}

	/**
	 * 配置redis集群
	 * 
	 * @return
	 */
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration() {
		String[] hostPortArr = hostPorts.split(",");
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(Arrays.asList(hostPortArr));
		clusterConfiguration.setMaxRedirects(maxRedirects);
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
		return jedisConnectionFactory;
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
