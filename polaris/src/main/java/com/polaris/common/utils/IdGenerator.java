package com.polaris.common.utils;

import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public final class IdGenerator {

	public static void main(String[] args) {
		RedisAtomicLong atomicLong = new RedisAtomicLong(redisCounter, template, initialValue);
	}
	
}
