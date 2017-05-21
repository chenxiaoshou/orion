package com.polaris.manage.service.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.polaris.common.paging.PagingSupport;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.persist.mysql.order.dto.SearchOrderCriteria;
import com.polaris.manage.persist.mysql.order.pub.OrderDao;
import com.polaris.manage.service.order.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public Order save(Order order) {
		return this.orderDao.save(order);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public void delete(Order order) {
		this.orderDao.delete(order);
	}

	@Override
	public Order find(String orderId) {
		return this.orderDao.findOne(orderId);
	}

	@Override
	public List<Order> list() {
		return this.orderDao.findAll();
	}

	@Override
	public List<Order> list(SearchOrderCriteria criteria) {
		PagingSupport<Order> ps = this.search(criteria);
		return ps.getResults();
	}

	@Override
	public PagingSupport<Order> search(SearchOrderCriteria criteria) {
		return this.orderDao.search(criteria);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public Order modify(Order order) {
		return this.orderDao.save(order);
	}

}
