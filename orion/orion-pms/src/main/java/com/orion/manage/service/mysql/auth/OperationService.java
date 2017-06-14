package com.orion.manage.service.mysql.auth;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.Operation;

public interface OperationService {

	Operation save(Operation operation);

	void delete(Operation operation);

	Operation modify(Operation operation) throws ApiException;

	Operation find(String operationId);

	List<Operation> list();

	void save(Collection<Operation> operations);

	void deleteInBatch(Collection<Operation> operations);

}
