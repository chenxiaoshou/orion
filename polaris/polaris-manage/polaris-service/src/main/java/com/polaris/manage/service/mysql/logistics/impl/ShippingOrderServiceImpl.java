package com.polaris.manage.service.mysql.logistics.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.supports.QuerySupport;
import com.polaris.manage.model.mysql.logistics.ShippingOrder;
import com.polaris.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;
import com.polaris.manage.persist.mysql.logistics.pub.ShippingOrderDao;
import com.polaris.manage.service.mysql.logistics.ShippingOrderService;

@Service("shippingOrderService")
public class ShippingOrderServiceImpl implements ShippingOrderService {

	@Autowired
	private ShippingOrderDao shippingOrderDao;

	@Override
	public ShippingOrder save(ShippingOrder shippingOrder) {
		if (shippingOrder != null) {
			return this.shippingOrderDao.save(shippingOrder);
		}
		return null;
	}

	@Override
	public void delete(ShippingOrder shippingOrder) {
		if (shippingOrder != null) {
			this.shippingOrderDao.delete(shippingOrder);
		}
	}

	@Override
	public ShippingOrder modify(ShippingOrder shippingOrder) throws ApiException {
		if (shippingOrder == null) {
			return shippingOrder;
		}
		if (StringUtils.isBlank(shippingOrder.getId())) {
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + shippingOrder.toString() + "]");
		}
		return this.shippingOrderDao.save(shippingOrder);
	}

	@Override
	public ShippingOrder find(String shippingOrderId) {
		return this.shippingOrderDao.findOne(shippingOrderId);
	}

	@Override
	public List<ShippingOrder> list() {
		return this.shippingOrderDao.findAll();
	}

	@Override
	public List<ShippingOrder> searchShippingOrders(QuerySupport<SearchShippingOrderCriteria> criteria) {
		return this.shippingOrderDao.searchShippingOrders(criteria);
	}

}
