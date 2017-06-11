package com.orion.manage.persist.mysql.order.custom;

import com.orion.common.supports.PagingSupport;
import com.orion.common.supports.QuerySupport;
import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.persist.mysql.order.dto.SearchOrderCriteria;

public interface OrderCustomDao {

	PagingSupport<Order> search(QuerySupport<SearchOrderCriteria> criteria);

}
