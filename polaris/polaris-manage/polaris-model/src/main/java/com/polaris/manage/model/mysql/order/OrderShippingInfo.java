package com.polaris.manage.model.mysql.order;

import java.io.Serializable;

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
@Table(name = "pms_order_shipping_info")
public class OrderShippingInfo implements Serializable {
	
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
			@Parameter(name = "idLength", value = "16"), @Parameter(name = "perfix", value = "OSI")})
	@Column(name = "id", nullable = false, columnDefinition = "varchar(128) default '' comment '订单唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerCountry() {
		return buyerCountry;
	}

	public void setBuyerCountry(String buyerCountry) {
		this.buyerCountry = buyerCountry;
	}

	public String getBuyerState() {
		return buyerState;
	}

	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}

	public String getBuyerProvince() {
		return buyerProvince;
	}

	public void setBuyerProvince(String buyerProvince) {
		this.buyerProvince = buyerProvince;
	}

	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	public String getBuyerStreet() {
		return buyerStreet;
	}

	public void setBuyerStreet(String buyerStreet) {
		this.buyerStreet = buyerStreet;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
