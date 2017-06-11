package com.orion.manage.persist.mysql.logistics.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.common.supports.QuerySupport;
import com.orion.manage.model.mysql.logistics.ShippingOrder;
import com.orion.manage.persist.mysql.logistics.custom.ShippingOrderCustomDao;
import com.orion.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;

public interface ShippingOrderDao extends JpaRepository<ShippingOrder, String>, ShippingOrderCustomDao {

	List<ShippingOrder> searchShippingOrders(QuerySupport<SearchShippingOrderCriteria> criteria);
	
}
