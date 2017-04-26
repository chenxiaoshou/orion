package com.polaris.manage.persist.order.custom;

import java.util.List;

import com.polaris.manage.model.order.mysql.Order;
import com.polaris.manage.persist.order.dto.SearchOrderCriteria;

public interface OrderCustomDao {

	List<Order> searchOrders(SearchOrderCriteria criteria);
	
}
