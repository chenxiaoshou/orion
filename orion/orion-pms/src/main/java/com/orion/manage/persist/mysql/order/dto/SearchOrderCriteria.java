package com.orion.manage.persist.mysql.order.dto;

import java.time.LocalDateTime;

import com.orion.common.base.BaseCriteria;
import com.orion.manage.model.tools.dic.order.OrderStatusEnum;
import com.orion.manage.model.tools.dic.order.SaleChannelEnum;

public class SearchOrderCriteria extends BaseCriteria {

	private static final long serialVersionUID = 310136725164532419L;

	private String orderId;

	private String[] orderIds;

	private OrderStatusEnum status;

	private OrderStatusEnum[] statuses;

	private Double minTotalPrice;

	private Double maxTotalPrice;

	private SaleChannelEnum saleChannel;

	private SaleChannelEnum[] saleChannels;

	private LocalDateTime createStartTime;

	private LocalDateTime createEndTime;

	private LocalDateTime updateStartTime;

	private LocalDateTime updateEndTime;

	private LocalDateTime completeStartTime;

	private LocalDateTime completeEndTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public OrderStatusEnum[] getStatuses() {
		return statuses;
	}

	public void setStatuses(OrderStatusEnum[] statuses) {
		this.statuses = statuses;
	}

	public Double getMinTotalPrice() {
		return minTotalPrice;
	}

	public void setMinTotalPrice(Double minTotalPrice) {
		this.minTotalPrice = minTotalPrice;
	}

	public Double getMaxTotalPrice() {
		return maxTotalPrice;
	}

	public void setMaxTotalPrice(Double maxTotalPrice) {
		this.maxTotalPrice = maxTotalPrice;
	}

	public SaleChannelEnum getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(SaleChannelEnum saleChannel) {
		this.saleChannel = saleChannel;
	}

	public SaleChannelEnum[] getSaleChannels() {
		return saleChannels;
	}

	public void setSaleChannels(SaleChannelEnum[] saleChannels) {
		this.saleChannels = saleChannels;
	}

	public LocalDateTime getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(LocalDateTime createStartTime) {
		this.createStartTime = createStartTime;
	}

	public LocalDateTime getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(LocalDateTime createEndTime) {
		this.createEndTime = createEndTime;
	}

	public LocalDateTime getUpdateStartTime() {
		return updateStartTime;
	}

	public void setUpdateStartTime(LocalDateTime updateStartTime) {
		this.updateStartTime = updateStartTime;
	}

	public LocalDateTime getUpdateEndTime() {
		return updateEndTime;
	}

	public void setUpdateEndTime(LocalDateTime updateEndTime) {
		this.updateEndTime = updateEndTime;
	}

	public LocalDateTime getCompleteStartTime() {
		return completeStartTime;
	}

	public void setCompleteStartTime(LocalDateTime completeStartTime) {
		this.completeStartTime = completeStartTime;
	}

	public LocalDateTime getCompleteEndTime() {
		return completeEndTime;
	}

	public void setCompleteEndTime(LocalDateTime completeEndTime) {
		this.completeEndTime = completeEndTime;
	}

}
