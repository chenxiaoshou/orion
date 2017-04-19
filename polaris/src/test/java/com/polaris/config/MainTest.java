package com.polaris.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainTest {

	private static Logger logger = LogManager.getLogger(MainTest.class);

	public static void main(String[] args) {
		for (int i = 0; i<100000000; i++) {
			for (int j = 0; j<100000000; j++) {
				logger.trace("trace level");
				logger.debug("debug level");
				logger.info("info level");
				logger.warn("warn level");
				logger.error("error level");
				logger.fatal("fatal level");
			}
		}
	}

}
