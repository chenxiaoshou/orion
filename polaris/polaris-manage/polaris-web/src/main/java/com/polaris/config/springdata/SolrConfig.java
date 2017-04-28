package com.polaris.config.springdata;

import java.util.function.Consumer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import com.polaris.common.constant.SolrConstants;
import com.polaris.common.utils.JsonUtil;
import com.polaris.manage.model.solr.order.SolrOrderShippingInfo;

/**
 * Spring-data-solr
 * 
 * @author John
 *
 */
@Configuration
@EnableSolrRepositories(basePackages = {
		"com.polaris.manage.*.solr" }, multicoreSupport = true, queryLookupStrategy = Key.CREATE_IF_NOT_FOUND)
@PropertySource("classpath:config.properties")
public class SolrConfig {

	private static final String PROPERTY_NAME_SOLR_SERVER_URL = "solr.host";

	@Autowired
	private Environment env;

	@Bean
	public SolrClient solrClient() {
		String solrHost = env.getRequiredProperty(PROPERTY_NAME_SOLR_SERVER_URL);
		return new HttpSolrClient(solrHost);
	}

	@Bean
	public SolrTemplate solrTemplate() {
		return new SolrTemplate(solrClient(), SolrConstants.solrCoreName);
	}

	public static void main(String[] args) {
		SolrClient client = new HttpSolrClient("http://192.168.199.106:8080/solr/");
		SolrTemplate solrTemplate = new SolrTemplate(client);
		solrTemplate.setSolrCore(SolrConstants.solrCoreName);
		solrTemplate.afterPropertiesSet();
		// SolrOrderShippingInfo info = new SolrOrderShippingInfo();
		// info.setId("2");
		// info.setBuyerAddress("中国江苏省苏州市新区金枫路234号东创产业园E栋12楼1206室");
		// info.setBuyerCity("苏州市suzhou");
		// info.setBuyerCountry("中国China");
		// info.setBuyerEmail("momogoing@163.com");
		// info.setBuyerProvince("江苏省jiangsu");
		// info.setBuyerStreet("金枫路234号东创产业园E栋12楼1206室");
		// info.setOrderId("OD123");
		// System.out.println(JsonUtil.toJSON(info));
		// solrTemplate.saveBean(info);
		// solrTemplate.commit(SolrConstants.solrCoreName);
		Query query = new SimpleQuery();
		query.addCriteria(Criteria.where("id").in(new Object[] { "1", "2" }).and("buyerAddress").contains("中国"));
		query.addSort(new Sort(Sort.Direction.DESC, "id"));
		// query.addSort(new Sort(Sort.Direction.DESC, "id", "description"));
		// query.addSort(new Sort(Sort.Direction.DESC, "description").and(new
		// Sort(Sort.Direction.ASC, "id")));
		Page<SolrOrderShippingInfo> page = solrTemplate.query(SolrConstants.solrCoreName, query,
				SolrOrderShippingInfo.class);
		page.forEach(new Consumer<SolrOrderShippingInfo>() {
			@Override
			public void accept(SolrOrderShippingInfo info) {
				System.out.println(JsonUtil.toJSON(info));
			}
		});
	}

}
