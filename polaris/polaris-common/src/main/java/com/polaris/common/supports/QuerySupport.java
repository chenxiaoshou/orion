package com.polaris.common.supports;

public class QuerySupport<T> {
	
	private T query;

	private PagingInfo paging;

	public QuerySupport() {
	}

	public QuerySupport(PagingInfo paging, T query) {
		super();
		this.paging = paging;
		this.query = query;
	}

	public PagingInfo getPaging() {
		return paging;
	}

	public void setPaging(PagingInfo paging) {
		this.paging = paging;
	}

	public T getQuery() {
		return query;
	}

	public void setQuery(T query) {
		this.query = query;
	}

}
