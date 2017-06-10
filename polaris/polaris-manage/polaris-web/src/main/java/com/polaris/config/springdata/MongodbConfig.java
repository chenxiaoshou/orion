package com.polaris.config.springdata;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.polaris.audit.UserAuditorAware;
import com.polaris.common.converter.DateToLocalDateConverter;
import com.polaris.common.converter.DateToLocalDateTimeConverter;
import com.polaris.common.converter.LocalDateTimeToDateConverter;
import com.polaris.common.converter.LocalDateToDateConverter;
import com.polaris.common.converter.TimestampConverter;
import com.polaris.config.datasource.DataSourceConfig;

/**
 * Spring-data-mongodb
 * 
 * @author John
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = { "com.polaris.manage.*.mongo" })
@EnableMongoAuditing(auditorAwareRef = "mongoAuditorProvider", dateTimeProviderRef = "mongoDateTimeProvider", modifyOnCreate = false)
public class MongodbConfig extends AbstractMongoConfiguration {

	private static final Logger LOGGER = LogManager.getLogger(DataSourceConfig.class);

	@Autowired
	private Environment env;

	@Autowired
	private Mongo mongo;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongo.dbname");
	}

	@Bean
	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(env.getProperty("mongo.host"), env.getRequiredProperty("mongo.port", int.class));
	}

	/**
	 * 自定义转换器，处理date到timestamp的转换问题
	 */
	@Override
	public CustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(TimestampConverter.INSTANCE);
		converters.add(DateToLocalDateConverter.INSTANCE);
		converters.add(DateToLocalDateTimeConverter.INSTANCE);
		converters.add(LocalDateTimeToDateConverter.INSTANCE);
		converters.add(LocalDateToDateConverter.INSTANCE);
		return new CustomConversions(converters);
	}

	@PreDestroy
	public void destroy() throws Exception {
		try {
			mongo.close();
			closeMongoDbFactory();
			LOGGER.debug(String.format("Destroy mongo %s successful", mongo));
		} catch (Exception e) {
			LOGGER.debug(String.format("Destroy mongo %s error", mongo), e);
		}
	}

	private void closeMongoDbFactory() {
		MongoDbFactory mongoDbFactory = null;
		try {
			mongoDbFactory = mongoDbFactory();
			if (mongoDbFactory instanceof SimpleMongoDbFactory) {
				SimpleMongoDbFactory simpleMongoDbFactory = (SimpleMongoDbFactory) mongoDbFactory;
				simpleMongoDbFactory.destroy();
				LOGGER.debug(String.format("Destroy simpleMongoDbFactory %s successful", simpleMongoDbFactory));
			}
		} catch (Exception e) {
			LOGGER.debug(String.format("Destroy simpleMongoDbFactory %s error", mongoDbFactory), e);
		}
	}

	/**
	 * 添加操作时间审计
	 * 
	 * @return
	 */
	@Bean
	public DateTimeProvider mongoDateTimeProvider() {
		return CurrentDateTimeProvider.INSTANCE;
	}

	/**
	 * 添加操作人审计
	 */
	@Bean
	public AuditorAware<String> mongoAuditorProvider() {
		return new UserAuditorAware();
	}

	@Bean
	public MongoExceptionTranslator exceptionTranslator() {
		return new MongoExceptionTranslator();
	}

	@Bean
	public LoggingEventListener logginEventListener() {
		return new LoggingEventListener();
	}

	@PostConstruct
	public void init() throws Exception {
		// 初始化
	}

}
