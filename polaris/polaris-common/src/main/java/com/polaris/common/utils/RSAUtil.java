package com.polaris.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RSA公钥/私钥/签名工具包
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * 
 * @author John
 *
 */
public class RSAUtil {

	private static final Logger LOGGER = LogManager.getLogger(RSAUtil.class);

	/**
	 * 加密算法
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final int KEY_SIZE = 1024;

	private static final String PUBLIC_KEY = "RSAPublicKey";

	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private static KeyFactory keyFactory = null;

	private static final String PUBLIC_KEY_FILE_PATH = "classpath:keystore/publicKey.keystore";

	private static final String PRIVATE_KEY_FILE_PATH = "classpath:keystore/privateKey.keystore";

	private static final Map<String, Object> keyMap = new HashMap<>(2);

	/** 随JVM启动自动加载系统中的公钥私钥到内存中（分布式系统中，保持密钥文件的一致） */
	static {
		try {
			keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			initKey();
			LOGGER.error("RSA Public Key [" + getBase64PublicKey() + "] Private Key [" + getBase64PrivateKey() + "]");
		} catch (Exception e) {
			keyMap.clear();
			LOGGER.error("Init RSA key failure!", e);
		}
	}

	private RSAUtil() {
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
		return (PublicKey) keyMap.get(PUBLIC_KEY);
	}

	/**
	 * 获取RSAPrivateKey私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey() throws Exception {
		return (PrivateKey) keyMap.get(PRIVATE_KEY);
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

	/**
	 * 将base64数据解密为byte数组
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return CodecUtil.fromBase64(key);
	}

	/**
	 * 将byte数组数据加密为base64
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return CodecUtil.toBase64(key);
	}

	/**
	 * 从外部文件中加载密钥文件，转换为对象之后保存到keyMap中
	 * 
	 * @throws Exception
	 */
	public static void initKey() throws Exception {
		String base64PublicKey = readFromFile(PUBLIC_KEY_FILE_PATH);
		String base64PrivateKey = readFromFile(PRIVATE_KEY_FILE_PATH);
		keyMap.put(PUBLIC_KEY, getPublicKeyFromBase64(base64PublicKey));
		keyMap.put(PRIVATE_KEY, getPrivateKeyFromBase64(base64PrivateKey));
	}

	/**
	 * 生成密钥对
	 * 
	 * @throws Exception
	 */
	public static void genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		String publicKeyStr = encryptBASE64(publicKey.getEncoded());
		String privateKeyStr = encryptBASE64(privateKey.getEncoded());
		// 将密钥对写入到文件
		writeToFile(publicKeyStr, PUBLIC_KEY_FILE_PATH);
		writeToFile(privateKeyStr, PRIVATE_KEY_FILE_PATH);
	}

	/**
	 * 加密数据
	 * <p>
	 * RSA加密明文最大长度117字节,所以此处采用分段加密
	 * </p>
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
	 * <p>
	 * 解密要求密文最大长度为128字节,所以此处采用分段解密
	 * </p>
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

	private static void writeToFile(String publicKeyStr, String filePath) throws IOException {
		try (FileWriter fw = new FileWriter(filePath); BufferedWriter bw = new BufferedWriter(fw);) {
			bw.write(publicKeyStr);
		}
	}

	private static String readFromFile(String filePath) throws FileNotFoundException, IOException {
		try (FileReader fr = new FileReader(filePath); BufferedReader br = new BufferedReader(fr);) {
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			return sb.toString();
		}
	}

	public static String extractFilePath() {
		// TODO
		return null;
	}

	public static void main(String[] args) {
		try {
			genKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
