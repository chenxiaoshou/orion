package com.orion.manage.service.mysql.component.impl;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.orion.NormalBaseTest;
import com.orion.common.constant.RedisConstants;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.manage.model.tools.dic.order.SaleChannelEnum;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.component.RedisService;
import com.orion.manage.web.vo.order.Order4Get;

public class RedisServiceImplTest extends NormalBaseTest {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, UserInfoCache> redisTemplate;

	@Autowired
	private RedisTemplate<String, Order4Get> redisTemplate1;

	@Autowired
	private RedisService redisService;

	@Test
	public void showRedis() {
		Set<String> keys = stringRedisTemplate.keys("*");
		for (SourceTypeEnum source : SourceTypeEnum.values()) {
			for (String key : keys) {
				try {
					if (key.startsWith(RedisConstants.KEY_TOKEN_USERINFO + source.name() + "||")) {
						System.out.println(key);
						System.out.println(stringRedisTemplate.boundHashOps(key).entries());
					} else if (key.startsWith(RedisConstants.KEY_USERID_TOKEN + source.name() + "||")) {
						System.out.println(key);
						System.out.println(stringRedisTemplate.boundValueOps(key).get());
					}
					System.out.println("+++++++++++++++");
				} catch (Exception e) {
				}
				// stringRedisTemplate.delete(key);
			}
		}
	}

//	@Test
	public void clearRedis() {
		Set<String> keys = stringRedisTemplate.keys("*");
		for (String key : keys) {
			stringRedisTemplate.delete(key);
		}
	}

	// @Test
	public void testRedisTemplate() {
		UserInfoCache cache = new UserInfoCache();
		cache.setEmail("1231231@qq.com");
		cache.setLastLoginTime(LocalDateTime.now());
		redisTemplate.boundValueOps("test").set(cache);
		System.out.println(redisTemplate.boundValueOps("test").get());

		Order4Get get = new Order4Get();
		get.setCreateTime(LocalDateTime.now());
		get.setId("1");
		get.setSaleChannel(SaleChannelEnum.Ebay);
		redisTemplate1.boundValueOps("test1").set(get);
		System.out.println(redisTemplate1.boundValueOps("test1").get());
	}

	// @Test
	public void showAllKeyValues() {
		this.redisService.getUserIdToken(SourceTypeEnum.Desktop, "1");
	}

}
