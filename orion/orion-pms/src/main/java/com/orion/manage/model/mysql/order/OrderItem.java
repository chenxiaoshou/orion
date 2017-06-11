package com.orion.manage.model.mysql.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.orion.manage.model.mysql.BaseMysqlObject;

@Entity
@Table(name = "pms_order_item")
public class OrderItem extends BaseMysqlObject {

	private static final long serialVersionUID = 6531209452277919416L;

	private String orderId; // 父订单Id
	
	private String orderNo;
	
	private Double salePrice; // 单价

	private Integer quantity; // 购买的数量

	private String productId; // 产品Id

	private String productName; // 产品名

	@Column(name = "order_id", nullable = false, length = 64, updatable = false, insertable = false, columnDefinition = "varchar(64) default '' comment '订单唯一标识'")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "order_no", nullable = false, updatable = false, insertable = false, length = 24, columnDefinition = "varchar(24) default '' comment '订单编号'")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name = "sale_price", nullable = false, precision = 2, columnDefinition = "double(11,2) default 0.00 comment '订单子条目售价'")
	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	@Column(name = "quantity", nullable = false, columnDefinition = "int(11) default 0 comment '买家购买的数量'")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "product_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '产品唯一标识'")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "product_name", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '产品名称'")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
