package com.orion.manage.web.controller.order;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.orion.NormalBaseTest;
import com.orion.common.constant.SymbolicConstants;
import com.orion.common.utils.JsonUtil;
import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.model.tools.dic.order.OrderStatusEnum;
import com.orion.manage.model.tools.dic.order.SaleChannelEnum;

public class OrderControllerTest extends NormalBaseTest {

	@Test
	public void crudOrder() throws Exception {
		// 增加
		Order order = new Order();
		order.setPaymentAmount(18.88d);
		order.setSaleChannel(SaleChannelEnum.Amazon);
		order.setTotalPrice(18.88d);
		order.setStatus(OrderStatusEnum.STEP_AWAIT_PROCESSING);
		Order savedOrder;
		savedOrder = saveOrder(order);
		assertNotNull(savedOrder);
		System.err.println("save order >>>" + savedOrder);

		// 查找
		String orderId = savedOrder.getId();
		assertNotNull(orderId);
		assertNotSame(orderId, SymbolicConstants.EMPTY);
		Order getOrder = getOrder(orderId);
		System.err.println("get order 1 >>>" + getOrder);

		// 修改
		orderId = getOrder.getId();
		assertNotNull(orderId);
		assertNotEquals(orderId, SymbolicConstants.EMPTY);
		getOrder.setPaymentAmount(48.8d);
		getOrder.setTotalPrice(48.8d);
		getOrder.setStatus(OrderStatusEnum.STEP_COMPLETED_ORDER);
		updateOrder(getOrder);

		// 再次查找
		Order getOrder2 = getOrder(orderId);
		assertNotNull(getOrder2);
		System.err.println("get order 2 >>>" + getOrder2);

		// 删除
		deleteOrder(orderId);

	}

	private void deleteOrder(String orderId) throws Exception {
		mockMvc.perform(delete("/order/{orderId}", orderId)).andDo(print()).andExpect(status().isNoContent());
	}

	private void updateOrder(Order order4Update) throws Exception {
		String updateRequestJson = JsonUtil.toJSON(order4Update);
		mockMvc.perform(
				put("/order/update").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(updateRequestJson))
				.andDo(print()).andExpect(status().isNoContent());
	}

	private Order saveOrder(Order order) throws Exception {
		String saveRequestJson = JsonUtil.toJSON(order);
		MvcResult saveResult = mockMvc
				.perform(post("/order/add").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(saveRequestJson)
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").isNotEmpty()).andReturn();
		assertNotNull(saveResult);
		String saveResponse = saveResult.getResponse().getContentAsString();
		assertTrue(JsonUtil.isJsonStr(saveResponse));
		return JsonUtil.fromJSON(saveResponse, Order.class);
	}

	private Order getOrder(String orderId) throws Exception, UnsupportedEncodingException {
		MvcResult findResult = mockMvc
				.perform(get("/order/{orderId}", orderId).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(orderId)).andExpect(jsonPath("$.createTime").isNotEmpty())
				.andReturn();
		assertNotNull(findResult);
		String findResponse = findResult.getResponse().getContentAsString();
		assertTrue(JsonUtil.isJsonStr(findResponse));
		return JsonUtil.fromJSON(findResponse, Order.class);
	}

}
