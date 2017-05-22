package com.polaris.config.datasource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.opensource.dbhelp.DbHelper;
import com.polaris.common.exception.PolarisException;

@Configuration
@PropertySource("classpath:config.properties")
public class DataSourceConfig {

	private static final Logger LOGGER = LogManager.getLogger(DataSourceConfig.class);

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	/**
	 * 数据源，由具体数据库配置实现
	 */
	@Bean
	@Primary
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = null;
		try {
			dataSource = new DruidDataSource();
			dataSource.setUrl(env.getProperty("mysql.url"));
			dataSource.setUsername(env.getProperty("mysql.username"));
			dataSource.setPassword(env.getProperty("mysql.password"));
			dataSource.setInitialSize(Integer.parseInt(env.getProperty("mysql.initialSize")));
			dataSource.setMinIdle(Integer.parseInt(env.getProperty("mysql.minIdle")));
			dataSource.setMaxActive(Integer.parseInt(env.getProperty("mysql.maxActive")));
			dataSource.setMaxWait(Long.parseLong(env.getProperty("mysql.maxWait")));
			dataSource.setTimeBetweenEvictionRunsMillis(
					Long.parseLong(env.getProperty("mysql.timeBetweenEvictionRunsMillis")));
			dataSource
					.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("mysql.minEvictableIdleTimeMillis")));
			dataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("mysql.poolPreparedStatements")));
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(
					Integer.parseInt(env.getProperty("mysql.maxPoolPreparedStatementPerConnectionSize")));
			dataSource.setValidationQuery("SELECT 'x' from dual");
			dataSource.setTestWhileIdle(true);
			dataSource.setTestOnBorrow(false);
			dataSource.setTestOnReturn(false);
			// stat-sql统计，log4j2-日志记录，wall-sql防注入攻击
			dataSource.setFilters("stat,log4j2,wall");
			List<Filter> filters = new ArrayList<>();
			filters.add(log4j2Filter());
			filters.add(statFilter());
			dataSource.setProxyFilters(filters);
		} catch (Exception e) {
			LOGGER.error("init DruidDataSource error", e);
			if (dataSource != null) {
				dataSource.close();
			}
			throw new PolarisException("init DruidDataSource error", e);
		}
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
		return new DbHelper(transactionAwareDataSourceProxy());
	}

	@PreDestroy
	public void destroy() throws Exception {
		DruidDataSource druidDataSource = (DruidDataSource) dataSource;
		try {
			druidDataSource.close();
			LOGGER.debug(String.format("Destroy druidDataSource %s successful", druidDataSource));
		} catch (Exception e) {
			LOGGER.debug(String.format("Destroy druidDataSource %s error", druidDataSource), e);
		}
	}

	@PostConstruct
	public void init() throws Exception {
		// 初始化
	}

}
