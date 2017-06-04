package com.polaris.manage.model.solr.logistics;

import org.springframework.data.solr.core.mapping.SolrDocument;

import com.polaris.common.constant.SolrConstants;
import com.polaris.manage.model.solr.BaseSolrObject;

@SolrDocument(solrCoreName = SolrConstants.solrCoreName)
public class SolrShippingOrder extends BaseSolrObject {

	private static final long serialVersionUID = -8813854741172306150L;

}
