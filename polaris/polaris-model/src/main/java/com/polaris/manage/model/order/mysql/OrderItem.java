package com.polaris.manage.model.order.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="pms_order_item")
public class OrderItem {

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.saving.ecm.resource.entity.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "ODS")})
	private String id;
	
	@Column(name = "sale_price", nullable = false, columnDefinition = "double(11,2) default 0 comment '订单子条目售价'")
 	private double salePrice;
	
	@Column(name = "quantity", nullable = false, columnDefinition = "int(11) default 0 comment '买家购买的数量'")
	private int quantity;
	
	@Column(name = "product_name", nullable = false, columnDefinition = "varchar(255) default '' comment '产品名称'")
	private String productName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
