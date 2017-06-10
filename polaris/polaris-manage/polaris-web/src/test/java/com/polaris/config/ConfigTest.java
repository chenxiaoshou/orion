package com.polaris.config;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.polaris.NormalBaseTest;
import com.polaris.common.constant.RabbitmqConstants;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.model.tools.dic.order.OrderStatusEnum;

public class ConfigTest extends NormalBaseTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	// @Test
	public void testConfig() {
		Assert.assertNotNull(mongoTemplate);
		Set<String> collnames = mongoTemplate.getCollectionNames();
		for (String collName : collnames) {
			System.out.println(collName);
		}
	}

	// @Test
	public void testAmqpTemplate() {
		for (int i = 0; i < 5; i++) {
			Order obj = new Order();
			obj.setId("OD" + i);
			obj.setStatus(OrderStatusEnum.STEP_COMPLETED_ORDER);
			rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
			rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_PMS, RabbitmqConstants.ROUTINGKEY_PMS_ORDER, obj);
		}
		for (int i = 0; i < 5; i++) {
			Order order = (Order) rabbitTemplate.receiveAndConvert(RabbitmqConstants.QUEUE_PMS_ORDER);
			if (order != null) {
				System.out.println(order);
			}
		}
	}

	// @Test
	public void testJedisConnectionFactory() {
		System.out.println(
				jedisConnectionFactory.getClusterConnection().lPush("polaris".getBytes(), "very good".getBytes()));
		System.out.println(new String(jedisConnectionFactory.getClusterConnection().lPop("polaris".getBytes())));
	}

	@Test
	public void testRedisTemplate() {
		System.out.println(redisTemplate.getHashKeySerializer());
		System.out.println(redisTemplate.getHashValueSerializer());
	}

}
