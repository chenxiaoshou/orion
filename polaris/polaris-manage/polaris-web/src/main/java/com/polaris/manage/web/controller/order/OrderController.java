package com.polaris.manage.web.controller.order;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
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
import com.polaris.common.paging.PagingSupport;
import com.polaris.common.utils.BeanUtil;
import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.JsonUtil;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.service.order.OrderService;
import com.polaris.manage.web.vo.order.Order4Create;
import com.polaris.manage.web.vo.order.Order4Put;
import com.polaris.manage.web.vo.order.OrderQuery;
import com.polaris.manage.web.vo.test.Test4Get;

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
	 * @throws BeanCopyException
	 * @throws URISyntaxException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Order saveOrder(@RequestBody @Valid Order4Create order4Create, HttpServletRequest request) {
		Order order = new Order();
		BeanUtil.copyProperties(order4Create, order);
		Timestamp now = DateUtil.timestamp();
		order.setCreateTime(now);
		return this.orderService.save(order);
	}

	public static void main(String[] args) {
		Order order = new Order();
		order.setPaymentAmount(5.6d);
		order.setSaleChannel("Amazon");
		order.setStatus(1);
		order.setTotalPrice(5.6d);
		System.out.println(JsonUtil.toJSON(order));
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
	@RequestMapping(value = "/{orderId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateOrder(@PathVariable String orderId, @RequestBody @Valid Order4Put order4Put,
			HttpServletRequest request) {
		if (StringUtils.isBlank(orderId)) {
			throw new ApiException("order.id.null");
		}
		Order order = orderService.find(orderId);
		BeanUtil.copyProperties(order4Put, order);
		this.orderService.modify(order);
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
		Order order = this.orderService.find(orderId);
		if (order == null) {
			throw new ApiException("order.is_null");
		}
		return order;
	}
	
	/**
	 * 搜索Order
	 * 
	 * @param orderId
	 * @param request
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public PagingSupport<Order> search(@RequestBody @Valid OrderQuery orderQuery, HttpServletRequest request) {
		// TODO 
		return null;
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
		Order order = this.orderService.find(orderId);
		if (order != null) {
			this.orderService.delete(order);
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
