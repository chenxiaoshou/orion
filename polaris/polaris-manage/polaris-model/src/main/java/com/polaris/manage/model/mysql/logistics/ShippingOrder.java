package com.polaris.manage.model.mysql.logistics;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.polaris.manage.model.mysql.BaseMysqlObject;

/**
 * 物流订单
 * 
 * @author John
 *
 */
@Entity
@Table(name = "PMS_SHIPPING_ORDER")
public class ShippingOrder extends BaseMysqlObject {

	private static final long serialVersionUID = -294238168881496088L;

	private String orderId; // 订单id

	private Double freight; // 运费

	private String shipmentNumber; // 物流单号

	private String FreightForwarderNumber; // 货代单号

	private Timestamp deliveryTime; // 发货时间

	private Timestamp completeTime; // 完成时间

	@Id
	@Override
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "SO") })
	@AttributeOverride(name = "ID", column = @Column(name = "ID", nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "varchar(64) default '' comment '主键唯一标识'"))
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "FREIGHT", nullable = false, precision = 2, columnDefinition = "double(11,2) default 0.00 comment '运费'")
	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	@Column(name = "SHIPMENT_NUMBER", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '物流单号'")
	public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	@Column(name = "FREIGHT_FORWARDER_NUMBER", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '物流单号'")
	public String getFreightForwarderNumber() {
		return FreightForwarderNumber;
	}

	public void setFreightForwarderNumber(String freightForwarderNumber) {
		FreightForwarderNumber = freightForwarderNumber;
	}

	@Column(name = "DELIVERY_TIME", nullable = true, columnDefinition = "DATETIME default NULL comment '交运时间'")
	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "COMPLETE_TIME", nullable = true, columnDefinition = "DATETIME default NULL comment '完成时间'")
	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "ORDER_ID", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '订单id'")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
