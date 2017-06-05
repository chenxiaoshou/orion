package com.polaris.manage.web.controller.order;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.polaris.MockBaseTest;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.service.srv.order.OrderService;

/**
 * 由于JUnit4设置的rollback=TRUE，所以没有办法真正向数据库添加数据，导致测试查找方法时，没有数据可用。
 * 所以涉及查找相关方法的测试使用mockit模拟框架来测试，其他使用普通JUnit4进行测试
 * 
 * @author John
 *
 */
public class MockOrderControllerTest extends MockBaseTest {

	@Mock
	private OrderService mockOrderService; // 使用mock对象代替spring上下文中的对象

	@InjectMocks
	private OrderController orderController;// 标记需要注入mock对象（即上面的mockOrderService对象）的类

	@Override
	public void setUp() {
		super.setUp();
		MockitoAnnotations.initMocks(this); // 将mockOrderService注入到mockOrderController类中
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	/**
	 * Mockito测试风格，必须[mockMvc =
	 * MockMvcBuilders.standaloneSetup(orderController).build();]这种方式获取mockMvc
	 * 
	 * @throws Exception
	 */
	@Test
	public void findOrder() throws Exception {
		String orderId = "OD2017051715271400001";
		double paymentAmount = 19.9d;
		String saleChannel = "SMT";
		int status = 0;
		double totalPrice = 19.9d;
		Order order = new Order();
		order.setId(orderId);
		order.setPaymentAmount(paymentAmount);
		order.setSaleChannel(saleChannel);
		order.setStatus(status);
		order.setTotalPrice(totalPrice);
		when(mockOrderService.find(orderId)).thenReturn(order);
		mockMvc.perform(get("/order/{orderId}", orderId).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(orderId)).andExpect(jsonPath("$.status").value(status))
				.andExpect(jsonPath("$.saleChannel").value(saleChannel));
		verify(mockOrderService, times(1)).find(orderId); // 验证方法是否被mock且是否为所执行的参数调用1次
		verify(mockOrderService, never()).delete(order);
	}

}
