package com.polaris.manage.model.solr.order;

import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.polaris.common.constant.SolrConstants;

@SolrDocument(solrCoreName = SolrConstants.solrCoreName)
public class SolrOrderShippingInfo {
	
	@Indexed
	private String id;

	@Indexed
	private String orderId;

	@Indexed
	private String buyerName;

	@Indexed
	private String buyerPhone;

	@Indexed
	private String buyerAddress;
	
	@Indexed
	private String buyerEmail;
	
	@Indexed
	private String buyerCountry;
	
	@Indexed
	private String buyerState;

	@Indexed
	private String buyerProvince;
	
	@Indexed
	private String buyerCity;

	@Indexed
	private String buyerStreet;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "32"), @Parameter(name = "perfix", value = "SOSI")})
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

}
