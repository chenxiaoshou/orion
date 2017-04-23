package com.polaris.common.config;

/**
 * Spring-data-RabbitMQ 先暂时注销
 */
// @Configuration
//@PropertySource("classpath:config.properties")
// @EnableRabbit
public class RabbitmqConfig {/*

	@Autowired
	private Environment env;

	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(env.getProperty("rabbitmq.url"),
				env.getRequiredProperty("rabbitmq.port", Integer.class));
		factory.setUsername(env.getProperty("rabbitmq.username"));
		factory.setPassword(env.getProperty("rabbitmq.password"));
		factory.setChannelCacheSize(env.getRequiredProperty("rabbitmq.channelCacheSize", Integer.class));
		return factory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		AmqpAdmin admin = new RabbitAdmin(rabbitConnectionFactory());
		admin.declareQueue(new Queue(RabbitmqConstants.QUEUE_CMS_ORDER));
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
*/}
