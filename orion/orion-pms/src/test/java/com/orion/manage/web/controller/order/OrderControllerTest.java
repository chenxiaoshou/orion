package com.orion.manage.web.controller.order;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.orion.NormalBaseTest;
import com.orion.common.utils.JsonUtil;
import com.orion.common.utils.RandomUtil;
import com.orion.manage.model.tools.dic.order.OrderStatusEnum;
import com.orion.manage.model.tools.dic.order.SaleChannelEnum;
import com.orion.manage.web.vo.order.Order4Add;

public class OrderControllerTest extends NormalBaseTest {

	@Test
	public void addOrder() {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 100; i++) {
						try {
							createOrder();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < 100; i++) {
			try {
				createOrder();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void createOrder() throws Exception {
		Order4Add order4Add = new Order4Add();
		Double price = RandomUtil.randomDouble(1.00d, 99.d);
		order4Add.setPaymentAmount(price);
		order4Add.setSaleChannel(RandomUtil.randomFromArray(SaleChannelEnum.values()));
		order4Add.setStatus(RandomUtil.randomFromArray(OrderStatusEnum.values()));
		order4Add.setTotalPrice(price);
		saveOrder(order4Add);
	}

	private void saveOrder(Order4Add order4Add) throws Exception {
		String saveRequestJson = JsonUtil.toJSON(order4Add);
		MvcResult saveResult = mockMvc
				.perform(post("/order").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(saveRequestJson)
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isCreated())
				.andReturn();
		assertNotNull(saveResult);
	}

}
