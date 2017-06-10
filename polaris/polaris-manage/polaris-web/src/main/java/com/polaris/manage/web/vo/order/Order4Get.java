package com.polaris.manage.web.vo.order;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.polaris.common.constant.PatternConstants;
import com.polaris.manage.model.tools.dic.order.OrderStatusEnum;
import com.polaris.manage.model.tools.dic.order.SaleChannelEnum;

public class Order4Get implements Serializable {

	private static final long serialVersionUID = -1994953916375628438L;

	private String orderId; // 订单Id

	private OrderStatusEnum status; // 订单状态(默认0，代表未指定状态，不具有业务意义)

	private Double totalPrice; // 订单总金额

	private Double paymentAmount; // 实际已支付金额

	private SaleChannelEnum saleChannel; // 订单来源渠道

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private LocalDateTime createTime; // 创建时间

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private LocalDateTime updateTime; // 更新时间

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private LocalDateTime completeTime; // 完成时间

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
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

	public SaleChannelEnum getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(SaleChannelEnum saleChannel) {
		this.saleChannel = saleChannel;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public LocalDateTime getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(LocalDateTime completeTime) {
		this.completeTime = completeTime;
	}

}
