package com.polaris.common.utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.codec.Codecs;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.polaris.common.exception.PolarisException;

public final class JWTUtil {

	private static final Logger LOGGER = LogManager.getLogger(JWTUtil.class);

	private static RsaSigner signer;

	private static RsaVerifier verifier;

	private JWTUtil() {

	}

	static {
		try {
			signer = new RsaSigner((RSAPrivateKey) SecurityUtil.getPrivateKey());
			verifier = new RsaVerifier((RSAPublicKey) SecurityUtil.getPublicKey());
		} catch (Exception e) {
			LOGGER.error("init tokenUtil failure!", e);
		}
	}

	public byte[] enSign(String data) {
		byte[] content = Codecs.utf8Encode(data);
		return signer.sign(content);
	}

	/**
	 * 同步加密生成JWT
	 * 
	 * @param jwtHeader：Map<String,
	 *            String>
	 * @param payload：JSON字符串格式
	 * @return
	 */
	public synchronized static String encode(String payload) {
		Jwt jwt = JwtHelper.encode(payload, signer);
		return jwt.getEncoded();
	}

	/**
	 * 同步加密生成JWT, 添加自定义header
	 * 
	 * @param payload
	 * @param header
	 * @return
	 */
	public synchronized static String encode(String payload, Map<String, String> header) {
		Jwt jwt = JwtHelper.encode(payload, signer, header);
		return jwt.getEncoded();
	}

	/**
	 * 检测Token是否合法
	 */
	public synchronized static boolean verifyJwtToken(String jwtToken) {
		boolean isLegalToken = true;
		try {
			getPayload(jwtToken);
		} catch (Exception e) {
			LOGGER.error("Found illegal jwt token [" + jwtToken + "]", e);
			isLegalToken = false;
		}
		return isLegalToken;
	}

	/**
	 * 解密Jwt, 提取payload
	 * 
	 * @return
	 */
	public synchronized static String getPayload(String jwtToken) {
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, verifier);
			return jwt.getClaims();
		} catch (Exception e) {
			throw new PolarisException("无法获取到Payload数据，校验失败 [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * 解密Jwt, 提取header
	 * 
	 * @return
	 */
	public synchronized static String getHeader(String jwtToken) {
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, verifier);
			return new String(Base64.getDecoder().decode(jwt.getEncoded().split("\\.")[0]));
		} catch (Exception e) {
			throw new PolarisException("获取JWT的header数据失败 [" + e.getMessage() + "]", e);
		}
	}

}
