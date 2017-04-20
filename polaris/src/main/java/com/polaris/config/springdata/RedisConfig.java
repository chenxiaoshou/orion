package com.yxt.config.special;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisConfig
 */
@Configuration
@PropertySource("classpath:component.properties")
public class RedisConfig {
	@Value("${component.wxredis.read.hostName:10.10.115.87}")
	private String wxReadHostName;

	@Value("${component.wxredis.read.port:63781}")
	private String wxReadPort;

	@Value("${component.wxredis.read.password:}")
	private String wxReadPassword;

	@Bean
	public StringRedisTemplate wxReadStringRedisTemplate() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(wxReadHostName);
		jedisConnectionFactory.setPort(Integer.parseInt(wxReadPort));
		if (!StringUtils.isBlank(wxReadPassword)) {
			jedisConnectionFactory.setPassword(wxReadPassword);
		}
		jedisConnectionFactory.setUsePool(true);
		jedisConnectionFactory.setDatabase(0);
		jedisConnectionFactory.afterPropertiesSet();
		return new StringRedisTemplate(jedisConnectionFactory);
	}
}
