package com.polaris.common.paging;

public class PageInfo {

	private int pageSize; // 每页数据条数 
	
	private int offset; // 当前页数据的偏移量
	
	private int totalPages; // 按照当前limit数值计算出的总页数
	
	private int totalCount; // 总记录数

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
