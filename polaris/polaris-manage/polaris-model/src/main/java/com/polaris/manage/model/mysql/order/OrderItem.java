package com.polaris.manage.model.mysql.order;

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

@Entity
@Table(name = "PMS_ORDER_ITEM")
public class OrderItem extends BaseObject implements Serializable {

	private static final long serialVersionUID = 6531209452277919416L;

	private String id;

	private Double salePrice; // 单价

	private Integer quantity; // 购买的数量

	private String productId; // 产品Id

	private String productName; // 产品名

	private String orderId; // 父订单Id

	private String creator; // 创建者

	private Timestamp createTime; // 创建时间

	private String updater; // 更新者

	private Timestamp updateTime; // 更新时间

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "ODS") })
	@Column(name = "ID", nullable = false, columnDefinition = "varchar(64) default '' comment '主键唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "SALE_PRICE", nullable = false, precision = 2, columnDefinition = "double(11,2) default 0.00 comment '订单子条目售价'")
	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	@Column(name = "QUANTITY", nullable = false, columnDefinition = "int(11) default 0 comment '买家购买的数量'")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "PRODUCT_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '产品唯一标识'")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "PRODUCT_NAME", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '产品名称'")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "ORDER_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '订单Id'")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "CREATOR", nullable = false, columnDefinition = "varchar(64) default '' comment '创建者ID'")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATER", nullable = false, columnDefinition = "varchar(64) default '' comment '更新者ID'")
	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@Column(name = "UPDATE_TIME", nullable = true, columnDefinition = "DATETIME default NULL comment '更新时间'")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
