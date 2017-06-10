package com.polaris.manage.service.mysql.order;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.common.supports.PagingSupport;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.persist.mysql.order.dto.SearchOrderCriteria;

public interface OrderService {

	Order save(Order order);

	void delete(Order order);

	Order find(String orderId);

	List<Order> list();

	List<Order> list(SearchOrderCriteria criteria);
	
	PagingSupport<Order> search(SearchOrderCriteria criteria);

	Order modify(Order order) throws ApiException;

}
