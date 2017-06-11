package com.orion.security;

import org.junit.Assert;
import org.junit.Test;

import com.orion.NormalBaseTest;
import com.orion.common.utils.SpringUtil;
import com.orion.manage.service.mysql.order.OrderService;
import com.orion.manage.service.mysql.security.SecurityService;

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
