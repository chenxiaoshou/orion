package com.polaris.manage.persist.mysql.logistics.custom;

import java.util.List;

import com.polaris.manage.model.mysql.logistics.ShippingOrder;
import com.polaris.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;

public interface ShippingOrderCustomDao {

	public List<ShippingOrder> searchShippingOrders(SearchShippingOrderCriteria criteria);
	
}
