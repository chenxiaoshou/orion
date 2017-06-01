package com.polaris.common.utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.codec.Codecs;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.polaris.common.exception.PolarisException;

import net.sf.json.JSONObject;

public final class TokenUtil {

	private static final Logger LOGGER = LogManager.getLogger(TokenUtil.class);

	private static RsaSigner signer;

	private static RsaVerifier verifier;

	private TokenUtil() {

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
	 * 加密token
	 * 
	 * @param json
	 *            要加密的json
	 * @return
	 */
	public static String encode(JSONObject json) {
		Jwt jwt = JwtHelper.encode(json.toString(), signer);
		return jwt.getEncoded();
	}

	/**
	 *
	 * @param json
	 *            要加密的json
	 * @param headers
	 *            共享数据，这个可以JwtHelper.headers取得map得取得：{headers.key=headers.val,
	 *            alg=HS256, typ=JWT}
	 * @return
	 */
	public static String encode(JSONObject json, Map<String, String> headers) {
		Jwt jwt = JwtHelper.encode(json.toString(), signer, headers);
		return jwt.getEncoded();
	}

	/**
	 * 解密token|token自带了解密串，使用了公私对串再进行一次校验
	 * 
	 * @return
	 */
	public static JSONObject decode(String token) {
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(token, verifier);
			return JSONObject.fromObject(jwt.getClaims());
		} catch (Exception e) {
			throw new PolarisException("校验失败", e);
		}
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("hehe", "https://my.oschina.net/yifanxiang");
		String token = TokenUtil.encode(json);
		System.out.println(token);
		JSONObject info = TokenUtil.decode("解析到的串");
		System.out.println(info);
	}

}
