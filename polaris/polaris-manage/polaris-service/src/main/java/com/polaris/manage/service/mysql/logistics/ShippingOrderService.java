package com.polaris.manage.service.mysql.logistics;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.logistics.ShippingOrder;

public interface ShippingOrderService {

	ShippingOrder save(ShippingOrder shippingOrder);

	void delete(ShippingOrder shippingOrder);

	ShippingOrder modify(ShippingOrder shippingOrder) throws ApiException;

	ShippingOrder find(String shippingOrderId);

	List<ShippingOrder> list();

}
