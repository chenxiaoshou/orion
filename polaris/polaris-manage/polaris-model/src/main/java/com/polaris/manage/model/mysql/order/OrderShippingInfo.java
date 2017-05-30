package com.polaris.manage.model.mysql.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.polaris.common.base.BaseObject;

/**
 * 订单的交货人信息
 * 
 * @author John
 *
 */

@Entity
@Table(name = "PMS_ORDER_SHIPPING_INFO")
public class OrderShippingInfo extends BaseObject implements Serializable {

	private static final long serialVersionUID = -2180680201712063931L;

	private String id;

	private String orderId;

	private String buyerName;

	private String buyerPhone;

	private String buyerAddress;

	private String buyerEmail;

	private String buyerCountry;

	private String buyerState;

	private String buyerProvince;

	private String buyerCity;

	private String buyerStreet;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "16"), @Parameter(name = "perfix", value = "OSI") })
	@Column(name = "ID", nullable = false, columnDefinition = "varchar(64) default '' comment '订单物流信息唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "BUYER_NAME", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '收货人姓名'")
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	@Column(name = "BUYER_PHONE", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '收货人电话'")
	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	@Column(name = "BUYER_ADDRESS", nullable = false, length = 512, columnDefinition = "varchar(512) default '' comment '收货人地址'")
	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	@Column(name = "BUYER_EMAIL", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '收货人邮件'")
	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	@Column(name = "BUYER_COUNTRY", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '收货人国家'")
	public String getBuyerCountry() {
		return buyerCountry;
	}

	public void setBuyerCountry(String buyerCountry) {
		this.buyerCountry = buyerCountry;
	}

	@Column(name = "BUYER_STATE", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '收货人所在州'")
	public String getBuyerState() {
		return buyerState;
	}

	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}

	@Column(name = "BUYER_PROVINCE", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '收货人省份'")
	public String getBuyerProvince() {
		return buyerProvince;
	}

	public void setBuyerProvince(String buyerProvince) {
		this.buyerProvince = buyerProvince;
	}

	@Column(name = "BUYER_CITY", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '收货人城市'")
	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	@Column(name = "BUYER_STREET", nullable = false, length = 512, columnDefinition = "varchar(512) default '' comment '收货人街道'")
	public String getBuyerStreet() {
		return buyerStreet;
	}

	public void setBuyerStreet(String buyerStreet) {
		this.buyerStreet = buyerStreet;
	}

	@Column(name = "ORDER_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '订单唯一标识'")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
