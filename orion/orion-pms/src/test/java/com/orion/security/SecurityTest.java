package com.orion.security;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.orion.NormalBaseTest;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.common.utils.SpringUtil;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.component.RedisService;
import com.orion.manage.service.mysql.order.OrderService;
import com.orion.manage.service.mysql.security.SecurityService;

public class SecurityTest extends NormalBaseTest {

	@Autowired
	private RedisService redisService;

	// @Test
	public void TestSecurityServic() {
		SecurityService securityService = (SecurityService) SpringUtil.getBean("securityService");
		Assert.assertNotNull(securityService);
		System.out.println(securityService);

		OrderService orderService = (OrderService) SpringUtil.getBean("orderService");
		Assert.assertNotNull(orderService);
		System.out.println(orderService);
	}

	// @Test
	public void testToken() {
		String pctoken = redisService.getUserIdToken(SourceTypeEnum.Desktop, "1");
		System.out.println(pctoken);
		UserInfoCache info = redisService.getTokenUserInfo(SourceTypeEnum.Desktop, pctoken);
		System.out.println(info);
		System.out.println("++++++++++++++++++++++");
		String androidToken = redisService.getUserIdToken(SourceTypeEnum.Android, "1");
		System.out.println(androidToken);
		UserInfoCache tokenInfo = redisService.getTokenUserInfo(SourceTypeEnum.Android, androidToken);
		System.out.println(tokenInfo);
	}

}
