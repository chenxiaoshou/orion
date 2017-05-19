package com.polaris.manage.web.vo.order;

import java.io.Serializable;

public class Order4Put implements Serializable {

	private static final long serialVersionUID = -8570350494980804445L;

	private int status; // 订单状态(默认0，代表未指定状态，不具有业务意义)
	
	private double totalPrice; // 订单总金额
	
	private double paymentAmount; // 实际已支付金额
	
	private String saleChannel; // 订单来源渠道

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}
	
}
