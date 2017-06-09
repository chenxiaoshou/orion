package com.polaris.manage.service.mysql.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.Operation;

public interface OperationService {

	Operation save(Operation operation);

	void delete(Operation operation);

	Operation modify(Operation operation) throws ApiException;

	Operation find(String operationId);

	List<Operation> list();

}
