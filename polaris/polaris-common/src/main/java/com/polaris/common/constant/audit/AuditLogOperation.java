package com.polaris.common.constant.audit;

/**
 * 审计日志记录中使用的操作日志类型
 * @author John
 *
 */
public interface AuditLogOperation {

	String ADD_ONE = "addOne";
	
	String ADD_MANY = "addMany";
	
	String DELETE_ONE = "deleteOne";
	
	String DELETE_MANY = "deleteMany";
	
	String MODIFY_ONE = "modifyOne";
	
	String MODIFY_MANY = "modifyMany";
	
	String FIND_ONE = "findOne";
	
	String FIND_MANY = "findMany";
	
	String SEARCH = "search";
}
