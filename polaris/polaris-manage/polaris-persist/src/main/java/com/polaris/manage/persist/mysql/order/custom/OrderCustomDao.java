package com.polaris.manage.persist.mysql.order.custom;

import com.polaris.common.supports.PagingSupport;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.persist.mysql.order.dto.SearchOrderCriteria;

public interface OrderCustomDao {

	PagingSupport<Order> search(SearchOrderCriteria criteria);
	
}
