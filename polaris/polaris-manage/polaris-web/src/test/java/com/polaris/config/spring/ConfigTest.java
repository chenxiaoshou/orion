package com.polaris.config.spring;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.polaris.BaseTest;
import com.polaris.common.constant.RabbitmqConstants;
import com.polaris.manage.model.mysql.order.Order;

public class ConfigTest extends BaseTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
//	@Test
	public void testConfig() {
		Assert.assertNotNull(mongoTemplate);
		Set<String> collnames = mongoTemplate.getCollectionNames();
		for (String collName : collnames) {
			System.out.println(collName);
		}
	}
	
	@Test
	public void amqpTemplate() {
		for (int i=0; i<5; i++) {
			Order obj = new Order();
			obj.setId("OD" + i);
			obj.setStatus(i % 5);
			rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
			rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_PMS, RabbitmqConstants.ROUTINGKEY_PMS_ORDER, obj);
		}
		for (int i=0; i<5; i++) {
			Order order = (Order) rabbitTemplate.receiveAndConvert(RabbitmqConstants.QUEUE_PMS_ORDER);
			if (order != null) {
				System.out.println(order);
			}
		}
	}
	
}
