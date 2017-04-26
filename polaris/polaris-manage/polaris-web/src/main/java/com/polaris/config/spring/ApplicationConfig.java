package com.polaris.config.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.opensource.dbhelp.DbHelper;

/**
 * 该配置文件和其他几个springdata文件夹下的配置类，
 * 就相当于xml配置中的 ApplicationContext.xml，
 * 只不过分开为不同的配置类中
 * @author John
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.polaris" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class, ControllerAdvice.class }) })
@PropertySource("classpath:config.properties") // 注入配置文件
@EnableAspectJAutoProxy(proxyTargetClass = true) // 支持切面,设置proxyTargetClass参数用来指定是使用CGLIB基于类的代理还是使用jdk基于接口的代理，使用@Aspect注解
@EnableTransactionManagement // 支持事务,使用@Transational注解
@EnableScheduling // 支持定时任务,使用@Scheduled注解
@EnableAsync // 支持异步,使用@Async注解
//@EnableCaching
public class ApplicationConfig {
	
	private static final Logger LOGGER = LogManager.getLogger(ApplicationConfig.class);

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
			// stat-sql统计，log4j2-日志记录，wall-sql防注入攻击
			dataSource.setFilters("stat,log4j2,wall");
		} catch (SQLException e) {
			LOGGER.error("Druid设置过滤器失败", e);
		}
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(log4j2Filter());
		filters.add(statFilter());
		dataSource.setProxyFilters(filters);
		return dataSource;
	}
	
	private StatFilter statFilter() {
		StatFilter statFilter = new StatFilter();
		statFilter.setLogSlowSql(true); // 记录慢查询语句
		statFilter.setMergeSql(true); // 自动合并非参数化的sql
		statFilter.setSlowSqlMillis(2000); // 设置慢查询标准
		return statFilter;
	}

	private Log4j2Filter log4j2Filter() {
		Log4j2Filter Log4j2Filter = new Log4j2Filter();
		// 下面的四个参数调试环境下可以打开，生产环境要关闭掉，打印日志很耗资源。
		// 关闭之后，log4j2.xml中定义的日志druid相关的appender不会再接收到sql日志记录
		Log4j2Filter.setDataSourceLogEnabled(true);
		Log4j2Filter.setConnectionLogEnabled(true);
		Log4j2Filter.setResultSetLogEnabled(true);
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
