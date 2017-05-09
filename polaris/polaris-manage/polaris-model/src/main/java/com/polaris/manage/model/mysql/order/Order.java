package com.polaris.manage.model.mysql.order;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="pms_order", indexes = {
        @Index(columnList = "total_price", name = "idx_total_price")} )
public class Order implements Serializable {
	
	private static final long serialVersionUID = -8594808281318789626L;

	private String id; //订单号，预备采用Redis的RedisAtomicLong来生成唯一标识
	
	private int status; // 订单状态
	
	private double totalPrice; // 订单总金额
	
	private double paymentAmount; // 实际已支付金额
	
	private String saleChannel; // 订单来源渠道
	
	private Timestamp createTime; // 创建时间
	
	private Timestamp updateTime; // 更新时间
	
	private Timestamp completeTime; // 完成时间
	
	// 处于后期效率的考虑，这里不适用级联
	// 这里的一对多，默认是由多的一方来维护关系，这里mappedBy指向的是多的一方中的order字段，不加这个的话，jpa会自动生成一个中间表
	/*@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy="order")
	@JoinColumn(name = "order_id")
	private Set<OrderItem> orderItems = Collections.synchronizedSet(new HashSet<OrderItem>());*/
	
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "OD")})
	@Column(name = "id", nullable = false, columnDefinition = "varchar(128) default '' comment '订单唯一标识'")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "status", nullable = false, columnDefinition = "int(2) default 0 comment '订单状态'")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "total_price", nullable = false, precision=2, columnDefinition = "double(11,2) default 0.00 comment '订单总金额'")
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "payment_amount", nullable = false, precision=2, columnDefinition = "double(11,2) default 0.00 comment '实际已支付金额'")
	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Column(name = "sale_channel", nullable = false, length=10, columnDefinition = "varchar(10) default '' comment '订单来源渠道'")
	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	@Column(name = "create_time", nullable = false, updatable=false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", nullable = true, columnDefinition = "DATETIME default NULL comment '更新时间'")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "complete_time", nullable = true, columnDefinition = "DATETIME default NULL comment '完成时间'")
	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
