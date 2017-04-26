package com.polaris.manage.model.order.mysql;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 订单的交货人信息
 * 
 * @author John
 *
 */
@Entity
@Table(name = "pms_order_shipping")
public class OrderShipping {

	private String id;

	private double freight; // 运费

	private String shipmentNumber; // 物流单号

	private String FreightForwarderNumber; // 货代单号

	private Timestamp createTime; // 物流订单创建时间

	private Timestamp deliveryTime; // 发货时间

	private Timestamp completeTime; // 完成时间

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "OS") })
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "freight", nullable = false, columnDefinition = "double(11,2) default 0 comment '运费'")
	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	@Column(name = "shipment_number", nullable = false, columnDefinition = "varchar(128) default '' comment '物流单号'")
	public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	@Column(name = "freight_forwarder_number", nullable = false, columnDefinition = "varchar(128) default '' comment '物流单号'")
	public String getFreightForwarderNumber() {
		return FreightForwarderNumber;
	}

	public void setFreightForwarderNumber(String freightForwarderNumber) {
		FreightForwarderNumber = freightForwarderNumber;
	}

	@Column(name = "create_time", nullable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "delivery_time", nullable = true, columnDefinition = "DATETIME default NULL comment '交运时间'")
	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "complete_time", nullable = true, columnDefinition = "DATETIME default NULL comment '完成时间'")
	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}

}
