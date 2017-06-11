package com.polaris.config.springdata;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.MappingSolrConverter;
import org.springframework.data.solr.core.mapping.SimpleSolrMappingContext;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import com.polaris.common.constant.SolrConstants;

/**
 * Spring-data-solr
 * 
 * @author John
 *
 */
@Configuration
@EnableSolrRepositories(basePackages = {
		"com.polaris.manage.*.solr" }, multicoreSupport = true, queryLookupStrategy = Key.CREATE_IF_NOT_FOUND)
public class SolrConfig {

	private static final Logger LOGGER = LogManager.getLogger(SolrConfig.class);

	private static final String PROPERTY_NAME_SOLR_SERVER_URL = "solr.host";

	@Autowired
	private Environment env;

	@Autowired
	private SolrClient solrClient;

	@Bean
	public SolrClient solrClient() {
		String solrHost = env.getRequiredProperty(PROPERTY_NAME_SOLR_SERVER_URL);
		return new HttpSolrClient(solrHost);
	}

	@Bean
	public MappingSolrConverter solrConverter() {
		// 自定义转换器
		// mappingSolrConverter.setCustomConversions(customConversions);
		return new MappingSolrConverter(new SimpleSolrMappingContext());
	}

	@Bean
	public SolrTemplate solrTemplate() {
		return new SolrTemplate(solrClient, SolrConstants.solrCoreName);
	}

	@PreDestroy
	public void destroy() throws Exception {
		try {
			solrClient.close();
			LOGGER.trace(String.format("Destroy solrClient %s successful", solrClient));
		} catch (Exception e) {
			LOGGER.error(String.format("Destroy solrClient %s error", solrClient), e);
		}
	}

	@PostConstruct
	public void init() throws Exception {
		// 初始化
	}

	// public static void main(String[] args) {
	// SolrClient client = new
	// HttpSolrClient("http://192.168.199.106:8080/solr/");
	// SolrTemplate solrTemplate = new SolrTemplate(client);
	// solrTemplate.setSolrCore(SolrConstants.solrCoreName);
	// solrTemplate.afterPropertiesSet();
	// // SolrOrderShippingInfo info = new SolrOrderShippingInfo();
	// // info.setId("2");
	// // info.setBuyerAddress("中国江苏省苏州市新区金枫路234号东创产业园E栋12楼1206室");
	// // info.setBuyerCity("苏州市suzhou");
	// // info.setBuyerCountry("中国China");
	// // info.setBuyerEmail("momogoing@163.com");
	// // info.setBuyerProvince("江苏省jiangsu");
	// // info.setBuyerStreet("金枫路234号东创产业园E栋12楼1206室");
	// // info.setOrderId("OD123");
	// // System.out.println(JsonUtil.toJSON(info));
	// // solrTemplate.saveBean(info);
	// // solrTemplate.commit(SolrConstants.solrCoreName);
	// Query query = new SimpleQuery();
	// query.addCriteria(Criteria.where("id").in(new Object[] { "1", "2"
	// }).and("buyerAddress").contains("中国"));
	// query.addSort(new Sort(Sort.Direction.DESC, "id"));
	// // query.addSort(new Sort(Sort.Direction.DESC, "id", "description"));
	// // query.addSort(new Sort(Sort.Direction.DESC, "description").and(new
	// // Sort(Sort.Direction.ASC, "id")));
	// Page<SolrOrderShippingInfo> page =
	// solrTemplate.query(SolrConstants.solrCoreName, query,
	// SolrOrderShippingInfo.class);
	// page.forEach(new Consumer<SolrOrderShippingInfo>() {
	// @Override
	// public void accept(SolrOrderShippingInfo info) {
	// System.out.println(JsonUtil.toJSON(info));
	// }
	// });
	// }

}
