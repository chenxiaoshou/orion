package com.polaris.config.springdata;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
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
public class MongodbConfig extends AbstractMongoConfiguration {

	private static final Logger LOGGER = LogManager.getLogger(DataSourceConfig.class);

	@Autowired
	private Environment env;

	@Autowired
	private Mongo mongo;

	@Autowired
	public MongoDbFactory mongoDbFactory;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongo.dbname");
	}

	@Bean
	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(env.getProperty("mongo.host"), env.getRequiredProperty("mongo.port", int.class));
	}

	// 自定义转换器，处理date到timestamp的转换问题
	@Override
	public CustomConversions customConversions() {
		return new CustomConversions(Collections.singletonList(TimestampConverter.INSTANCE));
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
		if (mongoDbFactory instanceof SimpleMongoDbFactory) {
			SimpleMongoDbFactory simpleMongoDbFactory = (SimpleMongoDbFactory) mongoDbFactory;
			try {
				simpleMongoDbFactory.destroy();
				LOGGER.debug(String.format("Destroy simpleMongoDbFactory %s successful", simpleMongoDbFactory));
			} catch (Exception e) {
				LOGGER.debug(String.format("Destroy simpleMongoDbFactory %s error", simpleMongoDbFactory), e);
			}
		}
	}

	@PostConstruct
	public void init() throws Exception {
		// 初始化
	}

	// public static void main(String[] args) {
	// String uri = "mongodb://192.168.199.106:27017/polaris_v1";
	// MongoDbFactory dbFactory;
	// try {
	// dbFactory = new SimpleMongoDbFactory(new MongoClientURI(uri));
	// DbRefResolver dbRefResolver = new DefaultDbRefResolver(dbFactory);
	// MappingMongoConverter converter = new
	// MappingMongoConverter(dbRefResolver, new MongoMappingContext());
	// // 自定义转换器，处理date到timestamp的转换问题
	// converter.setCustomConversions(
	// new
	// CustomConversions((Collections.singletonList(TimestampConverter.INSTANCE))));
	// converter.afterPropertiesSet(); // 必须要这一步
	// MongoTemplate temp = new MongoTemplate(dbFactory, converter);
	// Query query = new Query();
	// query.addCriteria(Criteria.where("saleChannel").is("SMT"));
	// try {
	// System.out.println(JsonUtil.toJSON(temp.find(query, MongoOrder.class)));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// } catch (UnknownHostException e1) {
	// e1.printStackTrace();
	// }
	// }

}
