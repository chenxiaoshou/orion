package com.polaris.manage.persist.mysql.logistics.custom;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.polaris.manage.model.mysql.logistics.ShippingOrder;
import com.polaris.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;

@Repository
public class ShippingOrderDaoImpl implements ShippingOrderCustomDao,Serializable {

	private static final long serialVersionUID = -9196420964855562349L;

	@Override
	public List<ShippingOrder> searchShippingOrders(SearchShippingOrderCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
