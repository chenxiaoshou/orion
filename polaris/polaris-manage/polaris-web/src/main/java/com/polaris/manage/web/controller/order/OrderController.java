package com.polaris.manage.web.controller.order;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.BeanCopyException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.service.srv.order.OrderService;
import com.polaris.manage.web.databean.test.Test4Get;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	/**
	 * 新增Order
	 * 
	 * @param dataBean
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Order saveOrder(@RequestBody @Valid Order order, HttpServletRequest request) {
		Timestamp now = DateUtil.timestamp();
		order.setCreateTime(now);
		order.setUpdateTime(now);
		return this.orderService.saveOrder(order);
	}

	/**
	 * 更新Order
	 * 
	 * @param dataBean
	 * @param request
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws BeanCopyException 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateOrder(@RequestBody @Valid Order order, HttpServletRequest request) throws BeanCopyException {
		if (StringUtils.isBlank(order.getId())) {
			throw new ApiException("order.id.null");
		}
		order.setUpdateTime(DateUtil.timestamp());
		this.orderService.updateOrder(order);
	}

	/**
	 * 查找Order
	 * 
	 * @param orderId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Order findOne(@PathVariable String orderId, HttpServletRequest request) {
		Order order = this.orderService.findOne(orderId);
		if (order == null) {
			throw new ApiException("order.is_null");
		}
		return order;
	}

	/**
	 * 删除Order
	 * 
	 * @param orderId
	 * @param request
	 */
	@RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String orderId, HttpServletRequest request) {
		Order order = this.orderService.findOne(orderId);
		if (order != null) {
			this.orderService.deleteOrder(order);
		}
	}

	/**
	 * 测试应用
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Test4Get test(HttpServletRequest request) {
		LOGGER.warn("for testing ... ");
		Test4Get test4Get = new Test4Get();
		test4Get.setCode("1");
		test4Get.setResult("OK");
		return test4Get;
	}

}
