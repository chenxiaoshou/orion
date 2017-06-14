package com.orion.manage.service.mysql.auth;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.MapPrivilegeOperation;

public interface MapPrivilegeOperationService {

	MapPrivilegeOperation save(MapPrivilegeOperation mapPrivilegeOperation);

	void delete(MapPrivilegeOperation mapPrivilegeOperation);

	MapPrivilegeOperation modify(MapPrivilegeOperation mapPrivilegeOperation) throws ApiException;

	MapPrivilegeOperation find(String mapPrivilegeOperationId);

	List<MapPrivilegeOperation> list();

	void save(Collection<MapPrivilegeOperation> mapPrivilegeOperations);

	void deleteInBatch(Collection<MapPrivilegeOperation> mapPrivilegeOperations);

}
