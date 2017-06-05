package com.polaris.manage.web.vo.order;

import java.io.Serializable;

public class Order4Put implements Serializable {

	private static final long serialVersionUID = -8570350494980804445L;

	private Integer status; // 订单状态(默认0，代表未指定状态，不具有业务意义)
	
	private Double totalPrice; // 订单总金额
	
	private Double paymentAmount; // 实际已支付金额
	
	private String saleChannel; // 订单来源渠道

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
	
}
