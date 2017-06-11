package com.polaris.manage.persist.mysql.logistics.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.common.supports.QuerySupport;
import com.polaris.manage.model.mysql.logistics.ShippingOrder;
import com.polaris.manage.persist.mysql.logistics.custom.ShippingOrderCustomDao;
import com.polaris.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;

public interface ShippingOrderDao extends JpaRepository<ShippingOrder, String>, ShippingOrderCustomDao {

	List<ShippingOrder> searchShippingOrders(QuerySupport<SearchShippingOrderCriteria> criteria);
	
}
