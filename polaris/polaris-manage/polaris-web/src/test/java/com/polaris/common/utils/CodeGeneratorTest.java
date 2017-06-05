package com.polaris.common.utils;

import org.junit.Test;

import com.polaris.NormalBaseTest;

public class CodeGeneratorTest extends NormalBaseTest {

	@Test
	public void testGenerateOrderNo() {
		for (int i = 0; i < 16; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(CodeGenerator.generateOrderNo("OD", 18));
				}
			}).start();
		}
	}

}
