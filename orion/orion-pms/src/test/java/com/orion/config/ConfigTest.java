package com.orion.config;

import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.gson.JsonObject;
import com.orion.NormalBaseTest;
import com.orion.common.constant.RabbitmqConstants;
import com.orion.common.utils.RandomUtil;
import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.model.tools.dic.order.OrderStatusEnum;

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
				jedisConnectionFactory.getClusterConnection().lPush("orion".getBytes(), "very good".getBytes()));
		System.out.println(new String(jedisConnectionFactory.getClusterConnection().lPop("orion".getBytes())));
	}

	@Test
	public void testRedisTemplate() throws InterruptedException {
		String redisKey = "key_key";
		JsonObject json = new JsonObject();
		for (int i = 0; i < 100; i++) {
			String value = RandomUtil.randomString(100);
			json.addProperty("property_" + i, value);
			this.redisTemplate.boundHashOps(redisKey).put("property_" + i, value);
		}
		String redisKey1 = "key_key1";
		this.redisTemplate.boundValueOps(redisKey1).set(json.toString());
		Thread.sleep(10000L);
		
		long startTime = System.currentTimeMillis();
		Map<Object, Object> values = this.redisTemplate.boundHashOps(redisKey).entries();
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + " valaus[" + values.size() + "]");
		
		startTime = System.currentTimeMillis();
		String value = this.redisTemplate.boundValueOps(redisKey1).get();
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + " value [" + value + "]");
	}

}
