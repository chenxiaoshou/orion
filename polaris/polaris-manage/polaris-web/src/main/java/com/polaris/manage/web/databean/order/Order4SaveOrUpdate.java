package com.polaris.manage.web.databean.order;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 * 承接前端传输过来的更新信息，转换为数据库entity之后做新增或者更新使用
 * @author John
 *
 */
public class Order4SaveOrUpdate {

	@Range(max=5, min=1, message="order.status.range")
	private int status; // 订单状态（1-待处理， 2-待发货， 3-已发货， 4-已完成， 5-问题订单）
	
	@NotNull(message="order.totalprice.notnull")
	private double totalPrice; // 订单总金额
	
	private double paymentAmount; // 实际已支付金额
	
	@NotNull(message="order.salechannel.notnull")
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
