package com.polaris.manage.model.solr.order;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.polaris.common.constant.SolrConstants;
import com.polaris.manage.model.solr.BaseSolrObject;

@SolrDocument(solrCoreName = SolrConstants.solrCoreName)
public class SolrOrderShippingInfo extends BaseSolrObject {

	private static final long serialVersionUID = -2201215121362250422L;

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
