package com.polaris.manage.service.srv.order.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.polaris.common.exception.BeanCopyException;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.persist.mysql.order.dto.SearchOrderCriteria;
import com.polaris.manage.persist.mysql.order.pub.OrderDao;
import com.polaris.manage.service.srv.order.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public Order saveOrder(Order order) {
		return this.orderDao.save(order);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public void deleteOrder(Order order) {
		this.orderDao.delete(order);
	}

	@Override
	public Order findOne(String orderId) {
		return this.orderDao.findOne(orderId);
	}

	@Override
	public List<Order> searchOrders(SearchOrderCriteria criteria) {
		return this.orderDao.searchOrders(criteria);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public void updateOrder(Order order4Update) throws BeanCopyException {
		Order order = findOne(order4Update.getId());
		try {
			BeanUtils.copyProperties(order, order4Update);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanCopyException(e.getMessage(), e);
		}
		this.orderDao.save(order);
	}

}
