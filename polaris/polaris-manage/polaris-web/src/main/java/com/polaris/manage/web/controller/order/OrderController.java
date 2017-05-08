package com.polaris.manage.web.controller.order;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.ErrorCode;
import com.polaris.common.exception.ExceptionMessage;
import com.polaris.common.exception.ExceptionType;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.service.srv.order.OrderService;
import com.polaris.manage.web.databean.order.Order4SaveOrUpdate;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void saveOrder(@RequestBody @Valid Order4SaveOrUpdate dataBean, BindingResult result,
			HttpServletRequest request) {
		Order order = new Order();
		try {
			BeanUtils.copyProperties(order, dataBean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error(e.getMessage(), e);
			ExceptionMessage em = new ExceptionMessage(HttpStatus.BAD_REQUEST.value(), ExceptionType.ERROR.getDesc(),
					ErrorCode.CODE_10001.getErrCode(), e.getMessage(), "", e.getCause());
			throw new ApiException(HttpStatus.BAD_REQUEST, em);
		}
		this.orderService.saveOrder(order);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void test(@RequestBody @Valid Order4SaveOrUpdate dataBean, BindingResult result,
			HttpServletRequest request) {
		LOGGER.warn("for testing");
	}

}
