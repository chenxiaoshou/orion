package com.orion.manage.persist.mysql.logistics.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.orion.common.supports.QuerySupport;
import com.orion.manage.model.mysql.logistics.ShippingOrder;
import com.orion.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;

@Repository
public class ShippingOrderDaoImpl implements ShippingOrderCustomDao {

	@Override
	public List<ShippingOrder> searchShippingOrders(QuerySupport<SearchShippingOrderCriteria> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
