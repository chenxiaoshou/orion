package com.polaris.manage.web.vo.order;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class Order4Create implements Serializable {

	private static final long serialVersionUID = -1100351041463473872L;

	@Range(min = 1, max = 5, message = "order.status.over_range")
	private Integer status; // 订单状态(默认0，代表未指定状态，不具有业务意义)

	@NotNull(message = "order.totalprice.not_null")
	private Double totalPrice; // 订单总金额

	private Double paymentAmount; // 实际已支付金额

	@NotBlank(message = "order.salechannel.not_blank")
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
