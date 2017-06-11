package com.orion.manage.model.mysql.logistics;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.orion.manage.model.mysql.BaseMysqlObject;

/**
 * 物流订单
 * 
 * @author John
 *
 */
@Entity
@Table(name = "pms_shipping_order")
public class ShippingOrder extends BaseMysqlObject {

	private static final long serialVersionUID = -294238168881496088L;

	private String orderId; // 订单id

	private Double freight; // 运费

	private String shipmentNumber; // 物流单号

	private String FreightForwarderNumber; // 货代单号

	private LocalDateTime deliveryTime; // 发货时间

	private LocalDateTime completeTime; // 完成时间

	@Column(name = "freight", nullable = false, precision = 2, columnDefinition = "double(11,2) default 0.00 comment '运费'")
	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	@Column(name = "shipment_number", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '物流单号'")
	public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	@Column(name = "freight_forwarder_number", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '物流单号'")
	public String getFreightForwarderNumber() {
		return FreightForwarderNumber;
	}

	public void setFreightForwarderNumber(String freightForwarderNumber) {
		FreightForwarderNumber = freightForwarderNumber;
	}

	@Column(name = "delivery_time", nullable = true, columnDefinition = "DATETIME default NULL comment '交运时间'")
	public LocalDateTime getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalDateTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "complete_time", nullable = true, columnDefinition = "DATETIME default NULL comment '完成时间'")
	public LocalDateTime getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(LocalDateTime completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "order_id", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '订单id'")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
