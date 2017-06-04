package com.polaris.manage.model.mongo.order;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.polaris.common.base.BaseObject;

@Document(collection="pms_order")
public class MongoOrder extends BaseObject implements Serializable {

	private static final long serialVersionUID = -3212665630676024012L;

	private String id; //订单号
	
	private Integer status; // 订单状态
	
	private Double totalPrice; // 订单总金额
	
	private Double paymentAmount; // 实际已支付金额
	
	private String saleChannel; // 订单来源渠道
	
	private Timestamp createTime; // 创建时间
	
	private Timestamp updateTime; // 更新时间
	
	private Timestamp completeTime; // 完成时间

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}
	
}
