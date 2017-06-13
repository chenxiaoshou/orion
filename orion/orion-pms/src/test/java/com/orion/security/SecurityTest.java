package com.orion.security;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.orion.NormalBaseTest;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.common.utils.SpringUtil;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.component.RedisService;
import com.orion.manage.service.mysql.order.OrderService;
import com.orion.manage.service.mysql.security.SecurityService;

public class SecurityTest extends NormalBaseTest {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

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

	@Test
	public void showRedis() {
		Set<String> keys = stringRedisTemplate.keys("*");
		for (String key : keys) {
			try {
				System.out.println(key);
				System.out.println(stringRedisTemplate.boundValueOps(key).get());
				System.out.println();
			} catch (Exception e) {
			}
//			stringRedisTemplate.delete(key);
		}
	}

}
