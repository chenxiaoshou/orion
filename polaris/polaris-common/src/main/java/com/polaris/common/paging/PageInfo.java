package com.polaris.common.paging;

import java.io.Serializable;

public class PageInfo implements Serializable {

	private static final long serialVersionUID = 2724475080681352450L;

	public static final int MAX_PAGESIZE = 1000;

	public static final int DEFAULT_PAGESIZE = 20;

	private int pageSize; // 每页数据条数

	private int offset; // 当前页数据的偏移量

	private int totalPages; // 按照当前limit数值计算出的总页数

	private int totalCount; // 总记录数

	private String sortFiled; // 排序字段

	private String direction; // 排序方向

	public int getPageSize() {
		if (pageSize <= 0 || pageSize > PageInfo.MAX_PAGESIZE) {
			this.pageSize = DEFAULT_PAGESIZE;
		}
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

	public int getCurPage() {
		this.offset = offset < 0 ? 0 : offset > totalCount ? totalCount : offset;
		this.pageSize = pageSize < 0 ? PageInfo.DEFAULT_PAGESIZE
				: pageSize > PageInfo.MAX_PAGESIZE ? PageInfo.MAX_PAGESIZE : pageSize;
		return offset / pageSize;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getSortFiled() {
		return sortFiled;
	}

	public void setSortFiled(String sortFiled) {
		this.sortFiled = sortFiled;
	}

}
