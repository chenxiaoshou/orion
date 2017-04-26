package com.polaris.manage.service.srv.logistics;

import java.util.List;

import com.polaris.manage.model.logistics.mysql.ShippingOrder;
import com.polaris.manage.persist.logistics.dto.SearchShippingOrderCriteria;

public interface ShippingService {

	public void saveShippingOrder(ShippingOrder shippingOrder);
	
	public void deleteShippingOrder(ShippingOrder shippingOrder);
	
	public ShippingOrder findShippingOrder(String id);
	
	public List<ShippingOrder> findAllShippingOrders();
	
	public List<ShippingOrder> searchShippingOrders(SearchShippingOrderCriteria criteria);
	
	// TODO 分页查询
	
}
