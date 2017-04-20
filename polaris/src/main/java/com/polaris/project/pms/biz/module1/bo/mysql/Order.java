package com.polaris.project.pms.biz.module1.bo.mysql;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="pms_order")
public class Order {
	
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.saving.ecm.resource.entity.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "OD")})
	private String id; //订单号，预备采用Redis的RedisAtomicLong来生成唯一标识
	
	@Column(name = "status", nullable = false, columnDefinition = "int(2) default 0 comment '订单状态'")
	private String status; // 订单状态
	
	@Column(name = "total_price", nullable = false, columnDefinition = "double(11,2) default 0 comment '订单总金额'")
	private double totalPrice; // 订单总金额
	
	@Column(name = "payment_amount", nullable = false, columnDefinition = "double(11,2) default 0 comment '实际已支付金额'")
	private double paymentAmount; // 实际已支付金额

	@Column(name = "sale_channel", nullable = false, columnDefinition = "varchar(10) default '' comment '订单来源渠道'")
	private String saleChannel; // 订单来源渠道
	
	@Column(name = "create_time", nullable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	private Timestamp createTime; // 创建时间
	
	@Column(name = "updat_time", nullable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '更新时间'")
	private Timestamp updateTime; // 更新时间
	
	@Column(name = "complete_time", nullable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '完成时间'")
	private Date completeTime; // 完成时间
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
}
