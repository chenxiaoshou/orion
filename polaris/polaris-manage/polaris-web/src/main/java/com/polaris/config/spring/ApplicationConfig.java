package com.polaris.config.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.opensource.dbhelp.DbHelper;

@Configuration
@ComponentScan(basePackages = { "com.polaris" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class, ControllerAdvice.class }) })
@PropertySource("classpath:config.properties") // 注入配置文件
@EnableAspectJAutoProxy(proxyTargetClass = true) // 支持切面,设置proxyTargetClass参数用来指定是使用CGLIB基于类的代理还是使用jdk基于接口的代理，使用@Aspect注解
@EnableTransactionManagement // 支持事务,使用@Transational注解
@EnableScheduling // 支持定时任务,使用@Scheduled注解
@EnableAsync // 支持异步,使用@Async注解
@EnableCaching
public class ApplicationConfig {

	@Autowired
	private Environment env;
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * 数据源，由具体数据库配置实现
	 */
	@Bean
	@Primary
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(env.getProperty("mysql.url"));
		dataSource.setUsername(env.getProperty("mysql.username"));
		dataSource.setPassword(env.getProperty("mysql.password"));
		dataSource.setInitialSize(Integer.parseInt(env.getProperty("mysql.initialSize")));
		dataSource.setMinIdle(Integer.parseInt(env.getProperty("mysql.minIdle")));
		dataSource.setMaxActive(Integer.parseInt(env.getProperty("mysql.maxActive")));
		dataSource.setMaxWait(Long.parseLong(env.getProperty("mysql.maxWait")));
		dataSource.setTimeBetweenEvictionRunsMillis(
				Long.parseLong(env.getProperty("mysql.timeBetweenEvictionRunsMillis")));
		dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("mysql.minEvictableIdleTimeMillis")));
		dataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("mysql.poolPreparedStatements")));
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(
				Integer.parseInt(env.getProperty("mysql.maxPoolPreparedStatementPerConnectionSize")));
		dataSource.setValidationQuery("SELECT 'x' from dual");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		try {
			dataSource.setFilters("stat,log4j");
		} catch (SQLException e) {
			// do nothing
		}
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(log4j2Filter());
		dataSource.setProxyFilters(filters);
		return dataSource;
	}

	private Log4j2Filter log4j2Filter() {
		Log4j2Filter Log4j2Filter = new Log4j2Filter();
		Log4j2Filter.setResultSetLogEnabled(false);
		Log4j2Filter.setStatementExecutableSqlLogEnable(true);
		return Log4j2Filter;
	}

	/**
	 * 通过一层Sping代理包装，是数据库连接具有感知事务上下文的能力，dbhelper这些非Spring插件可以使用包装过的包
	 * 
	 * @return
	 */
	private TransactionAwareDataSourceProxy transactionAwareDataSourceProxy() {
		return new TransactionAwareDataSourceProxy(dataSource);
	}

	/**
	 * dbHelper 封装了jdbctemplate的相关操作
	 * 
	 * @return
	 */
	@Bean
	public DbHelper dbHelper() {
		DbHelper appLogDbHelper = new DbHelper(transactionAwareDataSourceProxy());
		return appLogDbHelper;
	}

}
