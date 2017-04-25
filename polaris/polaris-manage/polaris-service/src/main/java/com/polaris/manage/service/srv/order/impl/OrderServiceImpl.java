package com.polaris.manage.service.srv.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.polaris.manage.model.order.mysql.Order;
import com.polaris.manage.persist.order.dto.SearchOrderCriteria;
import com.polaris.manage.persist.order.pub.IOrderDao;
import com.polaris.manage.service.srv.order.IOrderService;

@Service(value = "orderService")
public class OrderServiceImpl implements IOrderService {

	@Resource
	private IOrderDao iOrderDao;

	@Override
	public void saveOrder(Order order) {
		this.iOrderDao.save(order);
	}

	@Override
	public void deleteOrder(Order order) {
		this.iOrderDao.delete(order);
	}

	@Override
	public Order findOne(String orderId) {
		return this.iOrderDao.findOne(orderId);
	}

	@Override
	public List<Order> searchOrders(SearchOrderCriteria criteria) {
		return this.iOrderDao.searchOrders(criteria);
	}

}
