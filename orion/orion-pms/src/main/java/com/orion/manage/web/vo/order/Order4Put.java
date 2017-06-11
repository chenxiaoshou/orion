package com.orion.manage.web.vo.order;

import java.io.Serializable;

import com.orion.manage.model.tools.dic.order.OrderStatusEnum;
import com.orion.manage.model.tools.dic.order.SaleChannelEnum;

public class Order4Put implements Serializable {

	private static final long serialVersionUID = -8570350494980804445L;

	private OrderStatusEnum status; // 订单状态(默认0，代表未指定状态，不具有业务意义)

	private Double totalPrice; // 订单总金额

	private Double paymentAmount; // 实际已支付金额

	private SaleChannelEnum saleChannel; // 订单来源渠道

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

}
