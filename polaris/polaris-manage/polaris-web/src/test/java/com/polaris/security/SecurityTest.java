package com.polaris.security;

import org.junit.Assert;
import org.junit.Test;

import com.polaris.NormalBaseTest;
import com.polaris.common.utils.SpringUtil;
import com.polaris.manage.service.mysql.order.OrderService;
import com.polaris.security.service.SecurityService;

public class SecurityTest extends NormalBaseTest {

	@Test
	public void TestSecurityServic() {
		SecurityService securityService = (SecurityService) SpringUtil.getBean("securityService");
		Assert.assertNotNull(securityService);
		System.out.println(securityService);

		OrderService orderService = (OrderService) SpringUtil.getBean("orderService");
		Assert.assertNotNull(orderService);
		System.out.println(orderService);
	}

}
