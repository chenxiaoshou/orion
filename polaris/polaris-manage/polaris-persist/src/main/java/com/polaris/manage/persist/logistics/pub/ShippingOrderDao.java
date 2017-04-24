package com.polaris.manage.persist.logistics.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.logistics.mysql.ShippingOrder;
import com.polaris.manage.persist.logistics.custom.IShippingOrderCustomDao;

public interface ShippingOrderDao extends JpaRepository<ShippingOrder, String>, IShippingOrderCustomDao {

}
