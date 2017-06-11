package com.orion.manage.service.mysql.logistics;

import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.common.supports.QuerySupport;
import com.orion.manage.model.mysql.logistics.ShippingOrder;
import com.orion.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;

public interface ShippingOrderService {

	ShippingOrder save(ShippingOrder shippingOrder);

	void delete(ShippingOrder shippingOrder);

	ShippingOrder modify(ShippingOrder shippingOrder) throws ApiException;

	ShippingOrder find(String shippingOrderId);

	List<ShippingOrder> list();

	List<ShippingOrder> searchShippingOrders(QuerySupport<SearchShippingOrderCriteria> criteria);
	
}
