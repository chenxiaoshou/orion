package com.orion.manage.persist.mysql.order.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.persist.mysql.order.custom.OrderCustomDao;

/**
 * 继承Jpa提供的JpaRepository，同时继承自定义的OrderCustomDao，来定制自己的方法
 * 
 * @author John
 *
 */
public interface OrderDao extends JpaRepository<Order, String>, OrderCustomDao {

	Order findById(Long id);
	
}
