package com.polaris.manage.model.mysql.logistics;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.polaris.common.base.BaseObject;

/**
 * 物流订单
 * 
 * @author John
 *
 */
@Entity
@Table(name = "PMS_SHIPPING_ORDER")
public class ShippingOrder extends BaseObject implements Serializable {

	private static final long serialVersionUID = -294238168881496088L;

	private String id;

	private String orderId; // 订单id

	private Double freight; // 运费

	private String shipmentNumber; // 物流单号

	private String FreightForwarderNumber; // 货代单号

	private Timestamp createTime; // 物流订单创建时间

	private String creator;

	private Timestamp updateTime;

	private String updater;

	private Timestamp deliveryTime; // 发货时间

	private Timestamp completeTime; // 完成时间

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "SO") })
	@Column(name = "ID", nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "varchar(64) default '' comment '主键唯一标识'")
	public String getId() {
		return id;
	}

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

	@Column(name = "CREATE_TIME", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	@Column(name = "UPDATE_TIME", nullable = true, columnDefinition = "DATETIME default NULL comment '更新时间'")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATER", nullable = false, columnDefinition = "varchar(64) default '' comment '更新者ID'")
	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@Column(name = "CREATOR", nullable = false, columnDefinition = "varchar(64) default '' comment '创建者ID'")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}
