package com.polaris.manage.persist.logistics.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.polaris.manage.model.logistics.mysql.ShippingOrder;
import com.polaris.manage.persist.logistics.dto.SearchShippingOrderCriteria;

@Repository
public class ShippingOrderDaoImpl implements ShippingOrderCustomDao {

	@Override
	public List<ShippingOrder> searchShippingOrders(SearchShippingOrderCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
