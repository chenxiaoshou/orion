package com.polaris.manage.model.mysql.order;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.polaris.common.utils.CodeGenerator;
import com.polaris.manage.model.mysql.BaseMysqlObject;

@Entity
@Table(name = "pms_order")
public class Order extends BaseMysqlObject {

	private static final long serialVersionUID = -1189282556226768984L;

	private static final String ORDER_NO_PERFIX = "OD";

	private static final int ORDER_NO_LENGTH = 18;

	private String orderNo; // 订单编号

	private Integer status; // 订单状态(默认0，代表未指定状态，不具有业务意义)

	private Double totalPrice; // 订单总金额

	private Double paymentAmount; // 实际已支付金额

	private String saleChannel; // 订单来源渠道

	private Timestamp completeTime; // 完成时间

	@Column(name = "order_no", nullable = false, updatable = false, insertable = false, unique = true, length = 24, columnDefinition = "varchar(24) default '' comment '订单编号'")
	public String getOrderNo() {
		// 初始化orderNo
		if (StringUtils.isBlank(this.id) && StringUtils.isBlank(this.orderNo)) {
			orderNo = CodeGenerator.generateOrderNo(ORDER_NO_PERFIX, ORDER_NO_LENGTH);
		}
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "status", nullable = false, columnDefinition = "int(2) default 0 comment '订单状态'")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	@Column(name = "sale_channel", nullable = false, length = 16, columnDefinition = "varchar(16) default '' comment '订单来源渠道'")
	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	@Column(name = "complete_time", nullable = true, columnDefinition = "DATETIME default NULL comment '完成时间'")
	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}

}
