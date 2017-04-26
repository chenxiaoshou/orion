package com.polaris.manage.persist.logistics.custom;

import java.util.List;

import com.polaris.manage.model.logistics.mysql.ShippingOrder;
import com.polaris.manage.persist.logistics.dto.SearchShippingOrderCriteria;

public interface ShippingOrderCustomDao {

	public List<ShippingOrder> searchShippingOrders(SearchShippingOrderCriteria criteria);
	
}
