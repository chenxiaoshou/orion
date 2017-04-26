package com.polaris.config.springdata;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * Spring-data-solr
 * 
 * @author John
 *
 */
@Configuration
@EnableSolrRepositories
@PropertySource("classpath:config.properties")
public class SolrConfig {

}
