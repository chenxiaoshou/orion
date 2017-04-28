package com.polaris.config.springdata;

import java.net.UnknownHostException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.polaris.common.converter.TimestampConverter;
import com.polaris.common.utils.JsonUtil;
import com.polaris.manage.model.mongo.order.MongoOrder;

/**
 * Spring-data-mongodb
 * 
 * @author John
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = { "com.polaris.manage.*.mongo" })
@PropertySource("classpath:config.properties")
public class MongodbConfig extends AbstractMongoConfiguration {

	@Autowired
	private Environment env;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongo.dbname");
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(env.getProperty("mongo.host"), env.getRequiredProperty("mongo.port", int.class));
	}

	// 自定义转换器，处理date到timestamp的转换问题
	public CustomConversions customConversions() {
		return new CustomConversions(Collections.singletonList(TimestampConverter.INSTANCE));
	}

	public static void main(String[] args) {
		String uri = "mongodb://192.168.199.106:27017/polaris_v1";
		MongoDbFactory dbFactory;
		try {
			dbFactory = new SimpleMongoDbFactory(new MongoClientURI(uri));
			DbRefResolver dbRefResolver = new DefaultDbRefResolver(dbFactory);
			MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
			// 自定义转换器，处理date到timestamp的转换问题
			converter.setCustomConversions(
					new CustomConversions((Collections.singletonList(TimestampConverter.INSTANCE))));
			converter.afterPropertiesSet(); // 必须要这一步
			MongoTemplate temp = new MongoTemplate(dbFactory, converter);
			Query query = new Query();
			query.addCriteria(Criteria.where("saleChannel").is("SMT"));
			try {
				System.out.println(JsonUtil.toJSON(temp.find(query, MongoOrder.class)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

}
