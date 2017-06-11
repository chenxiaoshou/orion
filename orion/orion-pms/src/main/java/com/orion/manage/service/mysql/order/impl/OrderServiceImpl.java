package com.orion.manage.service.mysql.order.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orion.common.exception.AppException;
import com.orion.common.supports.PagingSupport;
import com.orion.common.supports.QuerySupport;
import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.persist.mysql.order.dto.SearchOrderCriteria;
import com.orion.manage.persist.mysql.order.pub.OrderDao;
import com.orion.manage.service.mysql.order.OrderService;

@Service("orderService")
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
	public List<Order> list(QuerySupport<SearchOrderCriteria> criteria) {
		PagingSupport<Order> ps = this.search(criteria);
		return ps.getResults();
	}

	@Override
	public PagingSupport<Order> search(QuerySupport<SearchOrderCriteria> criteria) {
		return this.orderDao.search(criteria);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public Order modify(Order order) {
		if (StringUtils.isBlank(order.getId())) {
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + order.toString() + "]");
		}
		return this.orderDao.save(order);
	}

}
