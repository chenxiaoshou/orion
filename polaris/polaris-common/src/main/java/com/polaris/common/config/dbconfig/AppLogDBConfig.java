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
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.opensource.dbhelp.DbHelper;

/**
 * 保存应用日志的数据源, 并不需要事务，所以不再配置相应的JPA事务
 * 
 * @author John
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = {
		"com.polaris.project.*.dao" }, entityManagerFactoryRef = "appLogEntityManagerFactory") // 支持JPA持久化解决方案
public class AppLogDBConfig {

	@Value("${applog.mysql.url}")
	private String url;

	@Value("${applog.mysql.username}")
	private String username;

	@Value("${applog.mysql.password}")
	private String password;

	@Value("${applog.mysql.initialSize}")
	private String initialSize;

	@Value("${applog.mysql.minIdle}")
	private String minIdle;

	@Value("${applog.mysql.maxActive}")
	private String maxActive;

	@Value("${applog.mysql.maxWait}")
	private String maxWait;

	@Value("${applog.mysql.timeBetweenEvictionRunsMillis}")
	private String timeBetweenEvictionRunsMillis;

	@Value("${applog.mysql.minEvictableIdleTimeMillis}")
	private String minEvictableIdleTimeMillis;

	@Value("${applog.mysql.poolPreparedStatements}")
	private String poolPreparedStatements;

	@Value("${applog.mysql.maxPoolPreparedStatementPerConnectionSize}")
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
	 * 保存应用日志的数据源
	 */
	@Bean
	public DataSource appLogDataSource() {
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
	public JpaVendorAdapter appLogVendorAdapter() {
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
	public EntityManagerFactory appLogEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(appLogVendorAdapter());
		factory.setPackagesToScan("com.polaris.project.*.bo.mysql");
		factory.setDataSource(appLogDataSource());
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
	 * dbHelper 封装了jdbctemplate的相关操作
	 * 
	 * @return
	 */
	@Bean
	public DbHelper appLogDbHelper() {
		DbHelper appLogDbHelper = new DbHelper(appLogDataSource());
		return appLogDbHelper;
	}

}
