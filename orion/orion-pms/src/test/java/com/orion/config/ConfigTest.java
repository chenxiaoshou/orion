package com.orion.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.orion.NormalBaseTest;
import com.orion.common.constant.RabbitmqConstants;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.model.tools.dic.order.OrderStatusEnum;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.component.RedisService;

public class ConfigTest extends NormalBaseTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisService redisService;

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

	// @Test
	public void testRedisTemplate() {
		System.out.println(redisTemplate.getHashKeySerializer());
		System.out.println(redisTemplate.getHashValueSerializer());
	}

	@Test
	public void testToken() {
		String pctoken = redisService.getUserIdToken(SourceTypeEnum.Desktop, "1");
		System.out.println(pctoken);
		UserInfoCache info = redisService.getTokenUserInfo(SourceTypeEnum.Desktop, pctoken);
		System.out.println(info);
		System.out.println("++++++++++++++++++++++");
		String androidToken = redisService.getUserIdToken(SourceTypeEnum.Android, "1");
		System.out.println(androidToken);
		UserInfoCache tokenInfo = redisService.getTokenUserInfo(SourceTypeEnum.Android, androidToken);
		System.out.println(tokenInfo);
	}

	// @Test
	public void showRedis() {
		Set<String> keys = stringRedisTemplate.keys("*");
		for (String key : keys) {
			stringRedisTemplate.delete(key);
		}
	}

	public static void main(String[] args) {
		System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(1497280545551L), ZoneId.systemDefault()));
	}

}
