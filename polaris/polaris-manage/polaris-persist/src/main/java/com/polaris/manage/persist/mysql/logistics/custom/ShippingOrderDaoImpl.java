package com.polaris.manage.persist.mysql.logistics.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.polaris.common.supports.QuerySupport;
import com.polaris.manage.model.mysql.logistics.ShippingOrder;
import com.polaris.manage.persist.mysql.logistics.dto.SearchShippingOrderCriteria;

@Repository
public class ShippingOrderDaoImpl implements ShippingOrderCustomDao {

	@Override
	public List<ShippingOrder> searchShippingOrders(QuerySupport<SearchShippingOrderCriteria> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
