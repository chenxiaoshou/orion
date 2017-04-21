package com.polaris.config.springdata;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * RabbitMQConfig
 */
@Configuration
@PropertySource("classpath:config.properties")
@EnableRabbit
public class RabbitmqConfig {
	@Value("${rabbitmq.url}")
	private String rabbitmqUrl;
	@Value("${rabbitmq.port}")
	private int rabbitmqPort;
	@Value("${rabbitmq.username}")
	private String userName;
	@Value("${rabbitmq.password}")
	private String passWord;

	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(rabbitmqUrl, rabbitmqPort);
		factory.setUsername(userName);
		factory.setPassword(passWord);
		factory.setChannelCacheSize(25);
		return factory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		AmqpAdmin admin = new RabbitAdmin(rabbitConnectionFactory());
		// admin.declareQueue(new Queue(Constants.QUEUE_SMS));
		return admin;
	}

	@Bean
	public AmqpTemplate amqpTemplate() {
		RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
		RetryTemplate retryTemplate = new RetryTemplate();
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(500);
		backOffPolicy.setMultiplier(10.0);
		backOffPolicy.setMaxInterval(10000);
		retryTemplate.setBackOffPolicy(backOffPolicy);
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		retryTemplate.setRetryPolicy(retryPolicy);
		template.setRetryTemplate(retryTemplate);
		return template;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(rabbitConnectionFactory());
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(rabbitConnectionFactory());
		factory.setConcurrentConsumers(2);
		factory.setMaxConcurrentConsumers(3);
		return factory;
	}
}
