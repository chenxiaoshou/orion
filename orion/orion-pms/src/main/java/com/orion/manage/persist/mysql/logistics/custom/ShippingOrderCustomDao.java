package com.orion.manage.persist.mysql.logistics.custom;

import java.util.List;

import com.orion.common.supports.QuerySupport;
import com.orion.manage.model.mysql.logistics.ShippingOrder;
import com.orion.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;

public interface ShippingOrderCustomDao {

	public List<ShippingOrder> searchShippingOrders(QuerySupport<SearchShippingOrderCriteria> criteria);
	
}
