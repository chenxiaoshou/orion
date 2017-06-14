package com.orion.manage.service.mysql.order;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.common.supports.PagingSupport;
import com.orion.common.supports.QuerySupport;
import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.persist.mysql.order.dto.SearchOrderCriteria;

public interface OrderService {

	Order save(Order order);

	void delete(Order order);

	Order find(String orderId);

	List<Order> list();

	List<Order> list(QuerySupport<SearchOrderCriteria> criteria);

	PagingSupport<Order> search(QuerySupport<SearchOrderCriteria> criteria);

	Order modify(Order order) throws ApiException;

	void save(Collection<Order> orders);

	void deleteInBatch(Collection<Order> orders);

}
