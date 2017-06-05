package com.polaris.manage.web.vo.order;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.polaris.common.constant.PatternConstants;
import com.polaris.common.paging.PageInfo;

public class OrderQuery implements Serializable {

	private static final long serialVersionUID = -3057402817845346655L;

	private String orderId;

	private String[] orderIds;

	private Integer minStatus;

	private Integer maxStatus;

	private Double minTotalPrice;

	private Double maxTotalPrice;

	private String saleChannel;

	private String[] saleChannels;

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp startCreateTime;

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp endCreateTime;

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp startUpdateTime;

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp endUpdateTime;

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp startCompleteTime;

	@JsonFormat(pattern = PatternConstants.DATE_FORMAT_PATTERN_1)
	private Timestamp endCompleteTime;

	private PageInfo pageInfo;

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public Integer getMinStatus() {
		return minStatus;
	}

	public void setMinStatus(Integer minStatus) {
		this.minStatus = minStatus;
	}

	public Integer getMaxStatus() {
		return maxStatus;
	}

	public void setMaxStatus(Integer maxStatus) {
		this.maxStatus = maxStatus;
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

	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	public String[] getSaleChannels() {
		return saleChannels;
	}

	public void setSaleChannels(String[] saleChannels) {
		this.saleChannels = saleChannels;
	}

	public Timestamp getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Timestamp startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Timestamp getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Timestamp endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Timestamp getStartUpdateTime() {
		return startUpdateTime;
	}

	public void setStartUpdateTime(Timestamp startUpdateTime) {
		this.startUpdateTime = startUpdateTime;
	}

	public Timestamp getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(Timestamp endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}

	public Timestamp getStartCompleteTime() {
		return startCompleteTime;
	}

	public void setStartCompleteTime(Timestamp startCompleteTime) {
		this.startCompleteTime = startCompleteTime;
	}

	public Timestamp getEndCompleteTime() {
		return endCompleteTime;
	}

	public void setEndCompleteTime(Timestamp endCompleteTime) {
		this.endCompleteTime = endCompleteTime;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

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

}
