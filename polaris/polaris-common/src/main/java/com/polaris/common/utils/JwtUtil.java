package com.polaris.common.utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.polaris.common.constant.PolarisConstants;
import com.polaris.common.exception.PolarisException;

import net.sf.json.JSONObject;

public final class JwtUtil {

	private static final Logger LOGGER = LogManager.getLogger(JwtUtil.class);

	/**
	 * jwt签发者（本服务器）
	 */
	public static final String CLAIMS_ISS = "iss";

	/**
	 * 面向的用户（此处用来存储user表唯一主键ID）
	 */
	public static final String CLAIMS_SUB = "sub"; 

	/**
	 * 接受jwt的一方（可以存储设备信息）
	 */
	public static final String CLAIMS_AUD = "aud";

	/**
	 * token过期时间，必须要大于签发时间
	 */
	public static final String CLAIMS_EXP = "exp";

	/**
	 * 定义一个时间，在该时间之前，此token都不可用
	 */
	public static final String CLAIMS_NBF = "nbf";

	/**
	 * token签发的时间
	 */
	public static final String CLAIMS_IAT = "iat";

	/**
	 * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
	 */
	public static final String CLAIMS_JTI = "jti"; 

	/**
	 * 传输到客户端的RSA公钥,供客户端加密数据使用
	 */
	public static final String CLAIMS_PUBLICKEY = "puk";
	
	/**
	 * 数据库中User的username
	 */
	public static final String CLAIMS_USERNAME = "username";

	private static RsaSigner signer;

	private static RsaVerifier verifier;

	private JwtUtil() {

	}

	static {
		try {
			signer = new RsaSigner((RSAPrivateKey) SecurityUtil.getPrivateKey());
			verifier = new RsaVerifier((RSAPublicKey) SecurityUtil.getPublicKey());
		} catch (Exception e) {
			LOGGER.error("init tokenUtil failure!", e);
		}
	}

	/**
	 * 同步加密生成JWT
	 * 
	 * @param payload：JSON字符串格式
	 * @return
	 */
	public static String encode(String payload) {
		Jwt jwt = JwtHelper.encode(payload, signer);
		return jwt.getEncoded();
	}
	
	/**
	 * 同步加密生成JWT
	 * 
	 * @param jwtHeader：Map<String,
	 *            String>
	 * @param payload：JSON字符串格式
	 * @return
	 */
	public static String encode(Map<String, Object> customPayloads) {
		Map<String, Object> payloads = buildNormalJwtPayloads();
		payloads.putAll(customPayloads);
		JSONObject jsonObject = JsonUtil.getJSONObject(payloads);
		Jwt jwt = JwtHelper.encode(jsonObject.toString(), signer);
		return jwt.getEncoded();
	}

	public static Map<String, Object> buildNormalJwtPayloads() {
		Map<String, Object> headers = new HashMap<>();
		headers.put(CLAIMS_ISS, PolarisConstants.DEFAULT_SERVLET_NAME);
		long createTime = DateUtil.now().getTime();
		headers.put(CLAIMS_IAT, createTime);
		long expirationTime = createTime + PolarisConstants.JWT_EXPIRATION;
		headers.put(CLAIMS_EXP, expirationTime);
		try {
			headers.put(CLAIMS_PUBLICKEY, SecurityUtil.getBase64PublicKey());
		} catch (Exception e) {
			LOGGER.error("获取RSA公钥失败！", e);
		}
		return headers;
	}
	
	/**
	 * 检测Token的第三部分签名是否合法（即没有被篡改过）
	 */
	public static boolean verifyJwtToken(String jwtToken) {
		boolean isLegalToken = true;
		try {
			JwtHelper.decodeAndVerify(jwtToken, verifier);
		} catch (Exception e) {
			LOGGER.error("JWT - 签名验证失败！ [" + jwtToken + "]", e);
			isLegalToken = false;
		}
		return isLegalToken;
	}

	/**
	 * 解密Jwt, 提取payload
	 * 
	 * @return
	 */
	public static JSONObject getPayload(String jwtToken) {
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, verifier);
			return JsonUtil.getJSONObject(jwt.getClaims());
		} catch (Exception e) {
			throw new PolarisException("获取JWT的payload数据失败！ [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * 解密Jwt, 提取header
	 * 
	 * @return
	 */
	public static JSONObject getHeader(String jwtToken) {
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, verifier);
			String header = new String(CodecUtil.fromBase64(jwt.getEncoded().split("\\.")[0]));
			return JsonUtil.getJSONObject(header);
		} catch (Exception e) {
			throw new PolarisException("获取JWT的header数据失败！ [" + e.getMessage() + "]", e);
		}
	}

	public static String getSignature(String jwtToken) {
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, verifier);
			return new String(CodecUtil.fromBase64(jwt.getEncoded().split("\\.")[2]));
		} catch (Exception e) {
			throw new PolarisException("获取JWT的signature数据失败！ [" + e.getMessage() + "]", e);
		}
	}

}
