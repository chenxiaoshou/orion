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
	
	private String id;
	
 	private double salePrice; // 单价
	
	private int quantity; //购买的数量
	
	private String productId; // 产品Id
	
	private String productName; // 产品名

	// 处于后期效率的考虑，这里不使用级联
	/*@ManyToOne(cascade = { CascadeType.MERGE}, fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name = "order_id")
	private Order order;*/
	
	private String orderId; // 父订单Id
	
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "ODS")})
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "sale_price", nullable = false, columnDefinition = "double(11,2) default 0 comment '订单子条目售价'")
	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	@Column(name = "quantity", nullable = false, columnDefinition = "int(11) default 0 comment '买家购买的数量'")
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(name = "product_id", nullable = false, columnDefinition = "varchar(64) default '' comment '产品唯一标识'")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Column(name = "product_name", nullable = false, columnDefinition = "varchar(255) default '' comment '产品名称'")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "order_id", nullable = false, columnDefinition = "varchar(64) default '' comment '订单Id'")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
