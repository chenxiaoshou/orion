package com.polaris.common.utils;

import org.apache.commons.lang3.RandomUtils;

public final class TestUtil {
	
	private static final String BASE_STR = "abcdefghijkmnpqrstuwxyz0123456789";

	private TestUtil() {
	}

	public static String createStringWithLength(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(BASE_STR.charAt(RandomUtils.nextInt(0, BASE_STR.length())));
		}
		return sb.toString();
	}
	
}
