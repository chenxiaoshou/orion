package com.polaris.manage.model.solr;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;

import com.polaris.common.base.BaseObject;

@MappedSuperclass
public class BaseSolrObject extends BaseObject {

	private static final long serialVersionUID = -8527624880875821522L;

	@Indexed
	protected String id;

	@Id
	public String getId() {
		return id;
	}

}
