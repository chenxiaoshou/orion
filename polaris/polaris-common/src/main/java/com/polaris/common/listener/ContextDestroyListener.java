package com.polaris.common.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 应用启动和停止的时候做一些资源初始化和清理的工作
 * 
 * @author John
 *
 */
public class ContextDestroyListener implements ServletContextListener {

	private static final Logger LOGGER = LogManager.getLogger(ContextDestroyListener.class);

	private static final List<String> MANUAL_DESTROY_THREAD_IDENTIFIERS = Arrays.asList("QuartzScheduler",
			"Abandoned");

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		destroyJDBCDrivers();
		destroySpecifyThreads();
		
	}

	@SuppressWarnings("deprecation")
	private void destroySpecifyThreads() {
		final Set<Thread> threads = Thread.getAllStackTraces().keySet();
		for (Thread thread : threads) {
			if (needManualDestroy(thread)) {
				synchronized (this) {
					try {
						thread.stop();
						LOGGER.debug(String.format("Destroy  %s successful", thread));
					} catch (Exception e) {
						LOGGER.warn(String.format("Destroy %s error", thread), e);
					}
				}
			}
		}
	}

	private boolean needManualDestroy(Thread thread) {
		final String threadName = thread.getName();
		for (String manualDestroyThreadIdentifier : MANUAL_DESTROY_THREAD_IDENTIFIERS) {
			if (threadName.contains(manualDestroyThreadIdentifier)) {
				return true;
			}
		}
		return false;
	}

	private void destroyJDBCDrivers() {
		final Enumeration<Driver> drivers = DriverManager.getDrivers();
		Driver driver;
		while (drivers.hasMoreElements()) {
			driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				LOGGER.debug(String.format("Deregister JDBC driver %s successful", driver));
			} catch (SQLException e) {
				LOGGER.warn(String.format("Deregister JDBC driver %s error", driver), e);
			}
		}
	}
}
