package com.polaris.manage.persist.mysql.order.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.persist.mysql.order.dto.SearchOrderCriteria;

@Repository
public class OrderDaoImpl implements OrderCustomDao {

	@Override
	public List<Order> searchOrders(SearchOrderCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
