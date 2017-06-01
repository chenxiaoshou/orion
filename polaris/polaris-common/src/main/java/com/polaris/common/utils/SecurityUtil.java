package com.polaris.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SecurityUtil {

	private static final Logger LOGGER = LogManager.getLogger(SecurityUtil.class);

	public static final String KEY_ALGORITHM = "RSA";

	private static final int KEY_SIZE = 1024;

	private static KeyFactory keyFactory = null;

	private static final String PUBLIC_KEY = "RSAPublicKey";

	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/** 准备一对已生成的公私密钥作为备用 */
	private static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDXqnVDfVQlKvdoB1/HGqcBqjqP3th2x/31tTwhOFCNAOJN2YcA4M7IKQK2XnIsAD/bXm23fwWCV78ySHEpQTr/3NdWnSHmwnzXuSRf4ZhTGLZWHf5E6jNRhJj2+Aj4lcG9d+AKqI8AmuM2Hl2mrM0SeyIiJ7DDGU8Rl6MF4EMCQIDAQAB";

	private static final String DEFAULT_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAINeqdUN9VCUq92gHX8capwGqOo/e2HbH/fW1PCE4UI0A4k3ZhwDgzsgpArZeciwAP9tebbd/BYJXvzJIcSlBOv/c11adIebCfNe5JF/hmFMYtlYd/kTqM1GEmPb4CPiVwb134AqojwCa4zYeXaaszRJ7IiInsMMZTxGXowXgQwJAgMBAAECgYBID2wfZzmySur/de3YJNFB5tFPNSVL5zPg8iH6ERmzA+8QnKfRJAgfLedt4B9Se2EAu59xNNErkVZeWUHBqTdKH7yshmc3MbrTBrvZijLIyCAGo0umC+HR3Ax1vCDXrHKmS2CeCFPzdQD+/izjZqbA7pyjXEjHiOty5dODdG5LgQJBAPLEh5i99Vs7SKAnMJv0kabaXeJHgwuvyWMm8eEUYfsYiRzShaT7cNumYK5XCtiieVnyoL0X6D2UJtueBQm6z1ECQQCKh7zcRRyxYETNFwECPtIgHJkIon2KTHo8C96d0xYoxWW+Dgs6kqYzKjySE4pnY9g+46bixshzmfWyfPPWb/M5AkBvcI/eKbTrgDdCbTr/HDGQKkVWjgU15CfKACKgc77WiNjIBkubBGE2MxXGceZks5CJHbtzkfnl6pA72Dnv0XVBAkAYdnHWX+n6NKrRoK9P6zIF86bejHso0ep/8gSk0CLInlsiHa7D8COjQ2Eg1oyJR2tnZ6IPx9Sb/WMS2tfgVTKZAkBGvAX4zahcRWXNry0POIT7NJ0TWLS0l/9sVHD+kC9W4XCX7ZUTfVAGjslDDFRPePKvuGVf1t3zLaJyh/D7LhJp";

	private static final Map<String, Object> keyMap = new HashMap<>(2);

	/** 随JVM启动自动生成一对RSA公私秘钥 */
	static {
		try {
			keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			initKey();
			LOGGER.error("RSA Public Key [" + getBase64PublicKey() + "]");
		} catch (Exception e) {
			keyMap.clear();
			LOGGER.error("Init RSA key failure!", e);
		}
	}

	private SecurityUtil() {

	}

	public static String getDefaultPublicKey() {
		return DEFAULT_PUBLIC_KEY;
	}

	public static String getDefaultPrivateKey() {
		return DEFAULT_PRIVATE_KEY;
	}

	/**
	 * 获取经过base64编码的公钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getBase64PublicKey() throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 获取经过base64编码的私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getBase64PrivateKey() throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 获取RSAPublicKey公钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey() throws Exception {
		PublicKey key = null;
		if (keyMap.get(PUBLIC_KEY) == null) {
			key = getPublicKeyFromBase64(DEFAULT_PUBLIC_KEY);
		} else {
			key = (PublicKey) keyMap.get(PUBLIC_KEY);
		}
		return key;
	}

	/**
	 * 获取RSAPrivateKey私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey() throws Exception {
		PrivateKey key = null;
		if (keyMap.get(PRIVATE_KEY) == null) {
			key = getPrivateKeyFromBase64(DEFAULT_PRIVATE_KEY);
		} else {
			key = (PrivateKey) keyMap.get(PRIVATE_KEY);
		}
		return key;
	}

	/**
	 * 从base64编码的公钥key字符串中得到PublicKey对象
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyFromBase64(String base64PublicKey) throws Exception {
		return keyFactory.generatePublic(new X509EncodedKeySpec(decryptBASE64(base64PublicKey)));
	}

	/**
	 * 从base64编码的私钥key字符串中得到PrivateKey对象
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyFromBase64(String base64PrivateKey) throws Exception {
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decryptBASE64(base64PrivateKey)));
	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.getDecoder().decode(key);
	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.getEncoder().encodeToString(key);
	}

	public static void initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
	}

	/**
	 * 加密数据
	 * 
	 * @param source
	 *            源数据
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String source) throws Exception {
		Key publicKey = getPublicKey();
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] sourceBytes = cipher.doFinal(source.getBytes());
		return encryptBASE64(sourceBytes);
	}

	/**
	 * 解密数据
	 * 
	 * @param cryptograph
	 *            密文
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cryptograph) throws Exception {
		Key privateKey = getPrivateKey();
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] cryptoBytes = decryptBASE64(cryptograph);
		return new String(cipher.doFinal(cryptoBytes), StandardCharsets.UTF_8);
	}

}
