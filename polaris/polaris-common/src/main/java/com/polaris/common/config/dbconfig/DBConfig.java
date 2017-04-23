package com.polaris.common.config.dbconfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.opensource.dbhelp.DbHelper;

/**
 * 保存应用业务数据的mysql数据源
 * 
 * @author dong
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = {
		"com.polaris.project.*.dao" }, entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager") // 支持JPA持久化解决方案
@EnableTransactionManagement // 开启事务
public class DBConfig {

	@Value("${mysql.url}")
	private String url;

	@Value("${mysql.username}")
	private String username;

	@Value("${mysql.password}")
	private String password;

	@Value("${mysql.initialSize}")
	private String initialSize;

	@Value("${mysql.minIdle}")
	private String minIdle;

	@Value("${mysql.maxActive}")
	private String maxActive;

	@Value("${mysql.maxWait}")
	private String maxWait;

	@Value("${mysql.timeBetweenEvictionRunsMillis}")
	private String timeBetweenEvictionRunsMillis;

	@Value("${mysql.minEvictableIdleTimeMillis}")
	private String minEvictableIdleTimeMillis;

	@Value("${mysql.poolPreparedStatements}")
	private String poolPreparedStatements;

	@Value("${mysql.maxPoolPreparedStatementPerConnectionSize}")
	private String maxPoolPreparedStatementPerConnectionSize;

	/**
	 * 配置之后，可以使用@value来获取到配置文件中的配置项
	 * 
	 * @return
	 */
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
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(Integer.parseInt(initialSize));
		dataSource.setMinIdle(Integer.parseInt(minIdle));
		dataSource.setMaxActive(Integer.parseInt(maxActive));
		dataSource.setMaxWait(Long.parseLong(maxWait));
		dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
		dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
		dataSource.setPoolPreparedStatements(Boolean.valueOf(poolPreparedStatements));
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(
				Integer.parseInt(maxPoolPreparedStatementPerConnectionSize));
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
		factory.setPackagesToScan("com.polaris.project.*.bo.mysql");
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
