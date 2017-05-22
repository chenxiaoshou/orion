package com.polaris.common.listener;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
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

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.polaris.common.utils.SpringUtil;

/**
 * 应用启动和停止的时候做一些资源初始化和清理的工作
 * 
 * @author John
 *
 */
public class ContextDestroyListener implements ServletContextListener {

	private static final Logger LOGGER = LogManager.getLogger(ContextDestroyListener.class);

	private static final List<String> MANUAL_DESTROY_THREAD_IDENTIFIERS = Arrays.asList("QuartzScheduler", "Abandoned");

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// init
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		deregisterJDBCDrivers(); // 注销JDBC驱动
		shutdownCleanupThread(); // 停止abandonedConnectionCleanupThread清理线程
		cleanSpringUtil();
//		destroySpecifyThreads();
//		destroyThreadLocals(); // 销毁ThreadLocal中的线程
	}

	private Integer destroyThreadLocals() {
		int count = 0;
		try {
			final Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
			threadLocalsField.setAccessible(true);
			final Field inheritableThreadLocalsField = Thread.class.getDeclaredField("inheritableThreadLocals");
			inheritableThreadLocalsField.setAccessible(true);
			for (final Thread thread : Thread.getAllStackTraces().keySet()) {
				count += clear(threadLocalsField.get(thread));
				count += clear(inheritableThreadLocalsField.get(thread));
				if (thread != null) {
					thread.setContextClassLoader(null);
				}
			}
			LOGGER.debug("Destroy " + count + " values in ThreadLocals");
		} catch (Exception e) {
			LOGGER.warn("Destroy thread in threadLocals error", e);
		}
		return count;
	}

	private int clear(final Object threadLocalMap) throws Exception {
		if (threadLocalMap == null)
			return 0;
		int count = 0;
		final Field tableField = threadLocalMap.getClass().getDeclaredField("table");
		tableField.setAccessible(true);
		final Object table = tableField.get(threadLocalMap);
		for (int i = 0, length = Array.getLength(table); i < length; ++i) {
			final Object entry = Array.get(table, i);
			if (entry != null) {
				final Object threadLocal = ((WeakReference<?>) entry).get();
				if (threadLocal != null) {
					LOGGER.debug("Destroy threadLocal successful");
					Array.set(table, i, null);
					++count;
				}
			}
		}
		return count;
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

	@SuppressWarnings("deprecation")
	private void destroySpecifyThreads() {
		final Set<Thread> threads = Thread.getAllStackTraces().keySet();
		for (Thread thread : threads) {
			if (needManualDestroy(thread)) {
				synchronized (this) {
					try {
						thread.setContextClassLoader(null);
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
