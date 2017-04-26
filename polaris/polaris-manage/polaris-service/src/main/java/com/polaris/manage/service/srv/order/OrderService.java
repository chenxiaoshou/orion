package com.polaris.manage.service.srv.order;

import com.polaris.manage.model.order.mysql.Order;

public interface OrderService {

	void saveOrder(Order order);
	
	void deleteOrder(Order order);
	
	Order findOne(String orderId);

//	List<Order> searchOrders(SearchOrderCriteria criteria);
	
}
