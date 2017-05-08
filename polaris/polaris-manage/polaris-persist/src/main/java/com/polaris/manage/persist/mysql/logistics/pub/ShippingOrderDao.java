package com.polaris.manage.persist.mysql.logistics.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.logistics.ShippingOrder;
import com.polaris.manage.persist.mysql.logistics.custom.ShippingOrderCustomDao;

public interface ShippingOrderDao extends JpaRepository<ShippingOrder, String>, ShippingOrderCustomDao {

}
