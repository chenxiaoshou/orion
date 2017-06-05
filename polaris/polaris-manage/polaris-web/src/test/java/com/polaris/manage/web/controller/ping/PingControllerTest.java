package com.polaris.manage.web.controller.ping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.polaris.NormalBaseTest;

public class PingControllerTest extends NormalBaseTest {

	@Test
	public void ping() throws Exception {
		mockMvc.perform(get("/ping").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.ack").value("success"));
	}

}
