package com.polaris.manage.web.databean.order;

/**
 * 承接前端传输过来的更新信息，转换为数据库entity之后做新增或者更新使用
 * @author John
 *
 */
public class Order4SaveOrUpdate {

	private String status; // 订单状态（1-待处理， 2-待发货， 3-已发货， 4-已完成， 5-问题订单）
	
	private double totalPrice; // 订单总金额
	
	private double paymentAmount; // 实际已支付金额
	
	private String saleChannel; // 订单来源渠道
	
}
