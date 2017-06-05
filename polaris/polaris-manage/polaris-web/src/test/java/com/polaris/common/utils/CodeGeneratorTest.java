package com.polaris.common.utils;

import org.junit.Test;

import com.polaris.NormalBaseTest;

public class CodeGeneratorTest extends NormalBaseTest {

	@Test
	public void testGenerateOrderNo() {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 500; i++) {
						System.out.println(CodeGenerator.generateOrderNo("OD", 18));
					}
				}
			}).start();
		}
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
