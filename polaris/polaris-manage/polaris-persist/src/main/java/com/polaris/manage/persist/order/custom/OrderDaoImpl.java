package com.polaris.manage.persist.order.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.polaris.manage.model.order.mysql.Order;
import com.polaris.manage.persist.order.dto.SearchOrderCriteria;

@Repository
public class OrderDaoImpl implements OrderCustomDao {

	@Override
	public List<Order> searchOrders(SearchOrderCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
