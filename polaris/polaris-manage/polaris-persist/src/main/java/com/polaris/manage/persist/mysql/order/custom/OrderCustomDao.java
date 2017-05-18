package com.polaris.manage.persist.mysql.order.custom;

import java.util.List;

import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.persist.mysql.order.dto.SearchOrderCriteria;

public interface OrderCustomDao {

	List<Order> search(SearchOrderCriteria criteria);
	
}
