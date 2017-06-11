package com.orion.config.springdata;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.orion.common.constant.RabbitmqConstants;

@Configuration
@EnableRabbit
public class RabbitmqConfig {

	private static final Logger LOGGER = LogManager.getLogger(RabbitmqConfig.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ConnectionFactory rabbitConnectionFactory;
	
	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(env.getProperty("rabbitmq.url"),
				env.getRequiredProperty("rabbitmq.port", Integer.class));
		factory.setUsername(env.getProperty("rabbitmq.username"));
		factory.setPassword(env.getProperty("rabbitmq.password"));
		factory.setVirtualHost(env.getProperty("rabbitmq.virtualhost"));
		factory.setConnectionTimeout(env.getProperty("rabbitmq.connectiontimeout", Integer.class));
		factory.setCacheMode(CacheMode.CHANNEL);
		factory.setChannelCacheSize(env.getRequiredProperty("rabbitmq.channelCacheSize", Integer.class));
		// rabbitmq支持发布确认和发布返回，这里只配置发布确认，未配置发布返回
		factory.setPublisherConfirms(true); // 发布者确认，使用目的待确认:这里需要显示调用才能进行消息的回调
		return factory;
	}
	
	/**
	 * 通过rabbitadmin接口为rabbitmq提供一组简单的便携式的操作
	 * @return
	 */
	@Bean
	public AmqpAdmin amqpAdmin() {
		AmqpAdmin admin = new RabbitAdmin(rabbitConnectionFactory());
		// channel_pms
		DirectExchange pmsExchange = new DirectExchange(RabbitmqConstants.EXCHANGE_PMS, true, false);
		admin.declareExchange(pmsExchange);
		// queue_pms_order
		Queue pmsOrderQueue = new Queue(RabbitmqConstants.QUEUE_PMS_ORDER, true, false, false);
		admin.declareQueue(pmsOrderQueue);
		admin.declareBinding(BindingBuilder.bind(pmsOrderQueue).to(pmsExchange).with(RabbitmqConstants.ROUTINGKEY_PMS_ORDER));
		// 可以根据需要声明更多的queue和binding规则
		return admin;
	}

	/**
	 * 消费者用来消费消息的工具Bean
	 * @return
	 */
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
		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 生产者用来发送消息的工具Bean,设置为原型模式，是为了发送消息之后的回调处理
	 * @return
	 */
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(rabbitConnectionFactory());
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		factory.setConnectionFactory(rabbitConnectionFactory());
		factory.setConcurrentConsumers(5);
		factory.setMaxConcurrentConsumers(10);
		factory.setMessageConverter(jackson2JsonMessageConverter());
		return factory;
	}
	
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@PreDestroy
	public void destroy() throws Exception {
		try {
			CachingConnectionFactory factory = (CachingConnectionFactory) rabbitConnectionFactory;
			factory.destroy();
			LOGGER.trace(String.format("Destroy rabbitConnectionFactory %s successful", rabbitConnectionFactory));
		} catch (Exception e) {
			LOGGER.error(String.format("Destroy rabbitConnectionFactory %s error", rabbitConnectionFactory), e);
		}
	}

	@PostConstruct
	public void init() throws Exception {
		// 初始化
	}
	
}
