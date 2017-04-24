package com.polaris.manage.web.controller.order;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.polaris.manage.model.order.mysql.Order;
import com.polaris.manage.service.srv.order.IOrderService;
import com.polaris.manage.web.databean.order.Order4SaveOrUpdate;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

	@Autowired
	private IOrderService orderService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void saveOrder(@RequestBody @Valid Order4SaveOrUpdate dataBean, HttpServletRequest request) {
		Order order = new Order();
		try {
			BeanUtils.copyProperties(order, dataBean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error(e.getMessage(), e);
		}
		this.orderService.saveOrder(order);
		
	}

}
