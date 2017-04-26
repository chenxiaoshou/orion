package com.polaris.config.springdata;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Spring-data-mongodb
 * 
 * @author John
 *
 */
@Configuration
@EnableMongoRepositories
@PropertySource("classpath:config.properties")
public class MongodbConfig {

}
