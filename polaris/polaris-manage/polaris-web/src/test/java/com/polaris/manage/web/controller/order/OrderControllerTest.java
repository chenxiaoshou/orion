package com.polaris.manage.web.controller.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.polaris.BaseTest;
import com.polaris.common.utils.JsonUtil;
import com.polaris.manage.web.databean.order.Order4SaveOrUpdate;

public class OrderControllerTest extends BaseTest {

	// @Test
	public void test() throws Exception {
		mockMvc.perform(get("/order/test").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.result").value("OK"));
	}

	@Test
	public void saveOrder() throws Exception {
		Order4SaveOrUpdate order4SaveOrUpdate = new Order4SaveOrUpdate();
		order4SaveOrUpdate.setPaymentAmount(19.9);
		order4SaveOrUpdate.setSaleChannel("SMT");
		order4SaveOrUpdate.setStatus(0);
		String requestJson = JsonUtil.toJSON(order4SaveOrUpdate);
		mockMvc.perform(post("/order/add").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestJson)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 验证响应contentType
				.andExpect(jsonPath("$.saleChannel").value("SMT"));
	}
	
	// TODO 查、改、删测试

}
