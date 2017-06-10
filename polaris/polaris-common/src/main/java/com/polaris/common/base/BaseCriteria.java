package com.polaris.common.base;

import java.beans.Transient;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.polaris.common.supports.PagingInfo;
import com.polaris.common.utils.JsonUtil;

public class BaseCriteria implements Serializable {

	private static final long serialVersionUID = -5496693067504600628L;

	private PagingInfo pagingInfo;

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	@Transient
	@JsonIgnore
	public String toJson() {
		return JsonUtil.toJSON(this);
	}

	@Transient
	@JsonIgnore
	public BaseCriteria fromJson(String json) {
		return JsonUtil.fromJSON(json, this.getClass());
	}

}
