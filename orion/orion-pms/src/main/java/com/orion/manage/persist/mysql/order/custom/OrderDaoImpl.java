package com.orion.manage.persist.mysql.order.custom;

import org.springframework.stereotype.Repository;

import com.orion.common.supports.PagingSupport;
import com.orion.common.supports.QuerySupport;
import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.persist.mysql.order.dto.SearchOrderCriteria;

@Repository
public class OrderDaoImpl implements OrderCustomDao {

	@Override
	public PagingSupport<Order> search(QuerySupport<SearchOrderCriteria> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
