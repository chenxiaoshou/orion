package com.polaris.common.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.polaris.common.utils.SpringUtil;

/**
 * 应用启动和停止的时候做一些资源初始化和清理的工作
 * 
 * @author John
 *
 */
public class ContextDestroyListener implements ServletContextListener {

	private static final Logger LOGGER = LogManager.getLogger(ContextDestroyListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// init
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		deregisterJDBCDrivers(); // 注销JDBC驱动
		shutdownCleanupThread(); // 停止abandonedConnectionCleanupThread清理线程
		cleanSpringUtil();
	}

	private void cleanSpringUtil() {
		try {
			SpringUtil.getInstance().close();
			LOGGER.debug("Destroy ApplicationContext in SpringUtil successful");
		} catch (Exception e) {
			LOGGER.warn("Destroy ApplicationContext in SpringUtil error", e);
		}
	}

	private void shutdownCleanupThread() {
		try {
			AbandonedConnectionCleanupThread.shutdown();
			LOGGER.debug("Destroy abandonedConnectionCleanupThread successful");
		} catch (Exception e) {
			LOGGER.warn("Destroy abandonedConnectionCleanupThread error", e);
		}
	}

	private void deregisterJDBCDrivers() {
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
