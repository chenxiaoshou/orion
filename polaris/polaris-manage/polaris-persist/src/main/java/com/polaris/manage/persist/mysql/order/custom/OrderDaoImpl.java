package com.polaris.manage.persist.mysql.order.custom;

import org.springframework.stereotype.Repository;

import com.polaris.common.supports.PagingSupport;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.persist.mysql.order.dto.SearchOrderCriteria;

@Repository
public class OrderDaoImpl implements OrderCustomDao {

	@Override
	public PagingSupport<Order> search(SearchOrderCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
