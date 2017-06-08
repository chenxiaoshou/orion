package com.polaris.manage.web.controller.order;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.polaris.common.exception.ApiException;
import com.polaris.common.paging.PagingSupport;
import com.polaris.common.utils.BeanUtil;
import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.JsonUtil;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.persist.mysql.order.dto.SearchOrderCriteria;
import com.polaris.manage.service.srv.order.OrderService;
import com.polaris.manage.web.controller.BaseController;
import com.polaris.manage.web.vo.order.Order4Add;
import com.polaris.manage.web.vo.order.Order4Put;
import com.polaris.manage.web.vo.order.OrderQuery;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	/**
	 * 新增Order
	 * 
	 * @param dataBean
	 * @param request
	 * @return
	 * @throws BeanCopyException
	 * @throws URISyntaxException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public String save(@RequestBody @Valid Order4Add order4Add, HttpServletRequest request) {
		Order order = new Order();
		BeanUtil.copyProperties(order4Add, order);
		Timestamp now = DateUtil.timestamp();
		order.setCreateTime(now);
		Order savedOrder = this.orderService.save(order);
		LOGGER.debug("save order [" + savedOrder.getId() + "]");
		return order.getId();
	}
	
	public static void main(String[] args) {
		Order4Add add = new Order4Add();
		add.setPaymentAmount(189.9d);
		add.setSaleChannel("SMT");
		add.setStatus(1);
		add.setTotalPrice(189.9d);
		System.out.println(JsonUtil.toJSON(add));
		
		System.out.println(new BCryptPasswordEncoder().encode("1990912"));
		
	}
	
	/**
	 * 更新Order
	 * 
	 * @param dataBean
	 * @param request
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws BeanCopyException
	 */
	@RequestMapping(value = "/{orderId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void modify(@PathVariable String orderId, @RequestBody @Valid Order4Put order4Put,
			HttpServletRequest request) {
		LOGGER.debug("modify order [" + orderId + "]");
		if (StringUtils.isBlank(orderId)) {
			throw new ApiException("order.id.null");
		}
		Order order = orderService.find(orderId);
		BeanUtil.copyProperties(order4Put, order);
		this.orderService.modify(order);
	}

	/**
	 * 查找Order
	 * 
	 * @param orderId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Order find(@PathVariable String orderId, HttpServletRequest request) {
		Order order = this.orderService.find(orderId);
		if (order == null) {
			throw new ApiException("order.is_null");
		}
		LOGGER.debug("find order [" + order.getId() + "]");
		return order;
	}

	/**
	 * 搜索Order
	 * 
	 * @param orderId
	 * @param request
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public PagingSupport<Order> search(@RequestBody @Valid OrderQuery orderQuery, HttpServletRequest request) {
		LOGGER.debug("search orders ... ");
		SearchOrderCriteria criteria = new SearchOrderCriteria();
		BeanUtil.copyProperties(orderQuery, criteria);
		return orderService.search(criteria);
	}

	/**
	 * 删除Order
	 * 
	 * @param orderId
	 * @param request
	 */
	@RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String orderId, HttpServletRequest request) {
		Order order = this.orderService.find(orderId);
		if (order != null) {
			LOGGER.debug("delete order [" + order.getId() + "]");
			this.orderService.delete(order);
		}
	}

}
