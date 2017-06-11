package com.polaris.manage.model.mysql.order;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.polaris.common.utils.CodeGenerator;
import com.polaris.manage.model.mysql.BaseMysqlObject;
import com.polaris.manage.model.tools.attributeconverter.OrderStatusAttributeConverter;
import com.polaris.manage.model.tools.attributeconverter.SaleChannelAttributeConverter;
import com.polaris.manage.model.tools.dic.order.OrderStatusEnum;
import com.polaris.manage.model.tools.dic.order.SaleChannelEnum;

@Entity
@Table(name = "pms_order")
public class Order extends BaseMysqlObject {

	private static final long serialVersionUID = -1189282556226768984L;

	private static final String ORDER_NO_PERFIX = "OD";

	private static final int ORDER_NO_LENGTH = 18;

	private String orderNo; // 订单编号

	private OrderStatusEnum status; // 订单状态(默认0，代表未指定状态，不具有业务意义)

	private Double totalPrice; // 订单总金额

	private Double paymentAmount; // 实际已支付金额

	private SaleChannelEnum saleChannel; // 订单来源渠道

	private LocalDateTime completeTime; // 完成时间

	@Column(name = "order_no", nullable = false, updatable = false, insertable = false, unique = true, length = 24, columnDefinition = "varchar(24) default '' comment '订单编号'")
	public String getOrderNo() {
		if (StringUtils.isBlank(this.orderNo)) {
			this.setOrderNo(CodeGenerator.generateOrderNo(ORDER_NO_PERFIX, ORDER_NO_LENGTH));
		}
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Convert(converter = OrderStatusAttributeConverter.class)
	@Column(name = "status", nullable = false, columnDefinition = "int(2) default 0 comment '订单状态'")
	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	@Column(name = "total_price", nullable = false, precision = 2, columnDefinition = "double(11,2) default 0.00 comment '订单总金额'")
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "payment_amount", nullable = false, precision = 2, columnDefinition = "double(11,2) default 0.00 comment '实际已支付金额'")
	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Convert(converter = SaleChannelAttributeConverter.class)
	@Column(name = "sale_channel", nullable = false, length = 16, columnDefinition = "varchar(16) default '' comment '订单来源渠道'")
	public SaleChannelEnum getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(SaleChannelEnum saleChannel) {
		this.saleChannel = saleChannel;
	}

	@Column(name = "complete_time", nullable = true, columnDefinition = "DATETIME default NULL comment '完成时间'")
	public LocalDateTime getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(LocalDateTime completeTime) {
		this.completeTime = completeTime;
	}

}
