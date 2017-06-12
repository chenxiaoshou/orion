package com.orion.manage.service.mysql.security;

import java.time.LocalDateTime;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("tokenService")
public interface TokenService {

	/**
	 * 生成jwt
	 * 
	 * @param userDetails
	 *            用户信息
	 * @param tokenSigner
	 *            token的签发者
	 * @param remoteHost
	 *            客户端IP
	 * @param device
	 *            客户端设备
	 * @return
	 */
	public String generateToken(UserDetails userDetails, String tokenSigner, String remoteHost, Device device);

	/**
	 * 判断当前用户是否能够刷新token
	 * 
	 * @param token
	 * @param lastPasswordResetTime
	 *            用户最后一次密码重置的时间
	 * @return
	 */
	public Boolean canTokenBeRefreshed(String token, LocalDateTime lastPasswordResetTime);

	/**
	 * 刷新token
	 * 
	 * @param token
	 * @return
	 */
	public String refreshToken(String token);

	/**
	 * 校验token是否合法
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean isTokenAvailable(String token, UserDetails userDetails);

	/**
	 * 从JWT中获取username
	 * 
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token);

	/**
	 * 从JWT中获取roles字符串拼接
	 * 
	 * @param token
	 * @return
	 */
	public String getRolesFromToken(String token);

	/**
	 * 从JWT中获取userId
	 * 
	 * @param token
	 * @return
	 */
	public String getUserIdFromToken(String token);

	/**
	 * 从JWT中获取token的签发创建时间
	 * 
	 * @param token
	 * @return
	 */
	public LocalDateTime getCreateTimeFromToken(String token);

	/**
	 * 从JWT中获取token的过期时间
	 * 
	 * @param token
	 * @return
	 */
	public LocalDateTime getExpirationDateFromToken(String token);

	/**
	 * 从JWT中获取发送请求客户端的Host
	 * 
	 * @param token
	 * @return
	 */
	public String getRemoteHostFromToken(String token);

	public Boolean isTabletOrMobile(String token);
	
	public Boolean isTokenWillExpire(String token);
	
	public Boolean isTokenExpired(String token);
	
	public Boolean isCreateTimeBeforeLastPasswordResetTime(LocalDateTime createTime, LocalDateTime lastPasswordReset);
	
	public boolean isRefrehedTimeValid(String token);
}
