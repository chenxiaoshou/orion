package com.polaris.manage.service.srv.logistics.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.polaris.manage.model.logistics.mysql.ShippingOrder;
import com.polaris.manage.persist.logistics.dto.SearchShippingOrderCriteria;
import com.polaris.manage.persist.logistics.pub.ShippingOrderDao;
import com.polaris.manage.service.srv.logistics.ShippingService;

public class ShippingServiceImpl implements ShippingService {

	@Autowired
	private ShippingOrderDao shippingOrderDao;
	
	@Override
	public void saveShippingOrder(ShippingOrder shippingOrder) {
		this.shippingOrderDao.save(shippingOrder);
	}

	@Override
	public void deleteShippingOrder(ShippingOrder shippingOrder) {
		this.shippingOrderDao.delete(shippingOrder);
	}

	@Override
	public ShippingOrder findShippingOrder(String id) {
		return this.shippingOrderDao.findOne(id);
	}

	@Override
	public List<ShippingOrder> findAllShippingOrders() {
		return this.shippingOrderDao.findAll();
	}

	@Override
	public List<ShippingOrder> searchShippingOrders(SearchShippingOrderCriteria criteria) {
		return this.shippingOrderDao.searchShippingOrders(criteria);
	}

}
