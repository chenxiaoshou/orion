package com.orion.manage.web.controller.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orion.common.exception.ApiException;
import com.orion.common.supports.PagingSupport;
import com.orion.common.supports.QuerySupport;
import com.orion.common.utils.BeanUtil;
import com.orion.manage.model.mysql.order.Order;
import com.orion.manage.persist.mysql.order.dto.SearchOrderCriteria;
import com.orion.manage.service.mysql.order.OrderService;
import com.orion.manage.web.controller.BaseController;
import com.orion.manage.web.vo.order.Order4Add;
import com.orion.manage.web.vo.order.Order4Get;
import com.orion.manage.web.vo.order.Order4Put;
import com.orion.manage.web.vo.order.OrderQuery;

@RestController
@RequestMapping({ "/order" })
public class OrderController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public String save(@RequestBody @Valid Order4Add order4Add, HttpServletRequest request,
			HttpServletResponse response) {
		Order order = new Order();
		BeanUtil.copyProperties(order4Add, order);
		Order savedOrder = this.orderService.save(order);
		LOGGER.debug("save order [" + savedOrder.getId() + "]");
		super.buildRedirectUrl(request, response, order.getId());
		return order.getId();
	}

	@RequestMapping(value = "/{orderId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void modify(@PathVariable String orderId, @RequestBody @Valid Order4Put order4Put,
			HttpServletRequest request) {
		if (StringUtils.isBlank(orderId)) {
			throw new ApiException("order.id.null");
		}
		Order order = this.orderService.find(orderId);
		if (order == null) {
			throw new ApiException("order.is_null");
		}
		BeanUtil.copyProperties(order4Put, order);
		this.orderService.modify(order);
	}

	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Order4Get find(@PathVariable String orderId, HttpServletRequest request) {
		Order order = this.orderService.find(orderId);
		if (order == null) {
			throw new ApiException("order.is_null");
		}
		LOGGER.debug("find order [" + order.getId() + "]");
		Order4Get order4Get = new Order4Get();
		BeanUtil.copyProperties(order, order4Get);
		return order4Get;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public PagingSupport<Order4Get> search(@RequestBody @Valid QuerySupport<OrderQuery> orderQuery,
			HttpServletRequest request) {
		QuerySupport<SearchOrderCriteria> criteria = new QuerySupport<>();
		BeanUtil.copyProperties(orderQuery, criteria);
		PagingSupport<Order> orders = this.orderService.search(criteria);
		PagingSupport<Order4Get> order4Gets = new PagingSupport<>();
		BeanUtil.copyProperties(orders, order4Gets);
		return order4Gets;
	}

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