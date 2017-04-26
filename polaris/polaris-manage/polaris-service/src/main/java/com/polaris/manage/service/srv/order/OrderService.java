package com.polaris.manage.service.srv.order;

import java.util.List;

import com.polaris.manage.model.order.mysql.Order;
import com.polaris.manage.persist.order.dto.SearchOrderCriteria;

public interface OrderService {

	void saveOrder(Order order);
	
	void deleteOrder(Order order);
	
	Order findOne(String orderId);

	List<Order> searchOrders(SearchOrderCriteria criteria);
	
}
