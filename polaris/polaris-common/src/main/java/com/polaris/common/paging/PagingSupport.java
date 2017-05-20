package com.polaris.common.paging;

import java.util.ArrayList;
import java.util.List;

public class PagingSupport<T> {

	private List<T> results = new ArrayList<>();

	private PageInfo pageInfo;

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

}
