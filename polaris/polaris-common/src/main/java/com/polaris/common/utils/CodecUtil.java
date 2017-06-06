package com.polaris.common.utils;

import java.util.Base64;

/**
 * 编解码相关的工具方法
 * @author John
 *
 */
public final class CodecUtil {

	private CodecUtil() {
		
	}
	
	public static String toBase64(byte[] content) {
		return Base64.getEncoder().encodeToString(content);
	}
	
	public static byte[] fromBase64(String base64Content) {
		return Base64.getDecoder().decode(base64Content);
	}
	
}
