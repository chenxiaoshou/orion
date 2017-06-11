package com.orion.manage.service.mysql.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public interface SecurityService {

	/**
	 * 获取用户的角色授权列表
	 * 
	 * @return
	 */
	public List<SimpleGrantedAuthority> getAuthorities(String userId);

	/**
	 * <p>
	 * 1.
	 * 根据userid删除旧的useid:token表中用户数据，因为这个表的唯一功能是用来维系“一个用户只有一个token”这种唯一性关系的表，其他业务API并不会在业务中查找此表
	 * 又因为是个Hash结构的表，无法为单条用户数据设置过期时间，所以需要手动删掉
	 * </p>
	 * <p>
	 * 2. 同时为了防止其他api接口此时正在使用老token从redis中获取用户信息，所以这里不会手动删除“token:
	 * userinfo”表中的旧token数据，等待它自动过期
	 * </p>
	 * 
	 * @param userId
	 * @param oldToken
	 * @param newToken
	 */
	public void refreshRedisToken(String userId, String oldToken, String newToken);

}
