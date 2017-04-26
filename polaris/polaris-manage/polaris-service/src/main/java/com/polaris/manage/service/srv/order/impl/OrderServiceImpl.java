package com.polaris.manage.service.srv.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.manage.model.order.mysql.Order;
import com.polaris.manage.persist.order.dto.SearchOrderCriteria;
import com.polaris.manage.persist.order.pub.IOrderDao;
import com.polaris.manage.service.srv.order.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderDao orderDao;
	
	@Override
	public void saveOrder(Order order) {
		this.orderDao.save(order);
	}

	@Override
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
	
}
