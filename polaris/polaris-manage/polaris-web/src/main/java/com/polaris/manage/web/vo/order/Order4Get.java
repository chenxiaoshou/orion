package com.polaris.manage.web.vo.order;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.polaris.common.constant.PatternConstants;

public class Order4Get implements Serializable {

	private static final long serialVersionUID = -1994953916375628438L;

	private String orderId; // 订单Id

	private Integer status; // 订单状态(默认0，代表未指定状态，不具有业务意义)

	private Double totalPrice; // 订单总金额

	private Double paymentAmount; // 实际已支付金额

	private String saleChannel; // 订单来源渠道

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp createTime; // 创建时间

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp updateTime; // 更新时间

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp completeTime; // 完成时间

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
