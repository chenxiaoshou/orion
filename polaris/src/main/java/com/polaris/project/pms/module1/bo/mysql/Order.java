package com.polaris.project.pms.module1.bo.mysql;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="pms_order")
public class Order {

	private String id;
	
	private String orderNo; // 订单号
	
	private double totalPrice; // 订单总金额
	
	private double paymentAmount; // 支付金额
	
}
