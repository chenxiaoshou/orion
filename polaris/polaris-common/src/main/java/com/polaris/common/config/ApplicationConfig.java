package com.polaris.common.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.opensource.dbhelp.DbHelper;

@Configuration
@ComponentScan(basePackages = { "com.polaris.*" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class, ControllerAdvice.class }) })
@PropertySource("classpath:config.properties") // 注入配置文件
@EnableAspectJAutoProxy(proxyTargetClass = true) // 支持切面,设置proxyTargetClass参数用来指定是使用CGLIB基于类的代理还是使用jdk基于接口的代理，使用@Aspect注解
@EnableTransactionManagement // 支持事务,使用@Transational注解
@EnableScheduling // 支持定时任务,使用@Scheduled注解
@EnableAsync // 支持异步,使用@Async注解
public class ApplicationConfig {

	@Autowired
	private Environment env;

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
	 * JPA实体转换器
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public JpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setShowSql(false);
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		return vendorAdapter;
	}

	/**
	 * JPA实体管理器工厂
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter());
		factory.setPackagesToScan("com.polaris.model..mysql");
		factory.setDataSource(dataSource());
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
		properties.put("hibernate.show_sql", false);
		properties.put("hibernate.format_sql", false);
		properties.put("hibernate.ejb.entitymanager_factory_name", "entityManagerFactory");
		properties.put("hibernate.hbm2ddl.auto", "update"); // 自动建表
		factory.setJpaProperties(properties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	/**
	 * 配置JPA事务管理器
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory());
		return transactionManager;
	}

	/**
	 * dbHelper 封装了jdbctemplate的相关操作
	 * 
	 * @return
	 */
	@Bean
	public DbHelper dbHelper() {
		DbHelper appLogDbHelper = new DbHelper(dataSource());
		return appLogDbHelper;
	}

}
