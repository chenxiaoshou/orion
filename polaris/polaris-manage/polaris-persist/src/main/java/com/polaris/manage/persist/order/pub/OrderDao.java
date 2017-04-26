package com.polaris.manage.persist.order.pub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polaris.manage.model.order.mysql.Order;

/**
 * 继承Jpa提供的JpaRepository，同时继承自定义的OrderCustomDao，来定制自己的方法
 * 
 * @author John
 *
 */
@Repository
public interface OrderDao extends JpaRepository<Order, String> {

}
