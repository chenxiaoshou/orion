package com.polaris.manage.service.srv.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.MapPrivilegeOperation;

public interface MapPrivilegeOperationService {

	MapPrivilegeOperation save(MapPrivilegeOperation mapPrivilegeOperation);

	void delete(MapPrivilegeOperation mapPrivilegeOperation);

	MapPrivilegeOperation modify(MapPrivilegeOperation mapPrivilegeOperation) throws ApiException;

	MapPrivilegeOperation find(String mapPrivilegeOperationId);

	List<MapPrivilegeOperation> list();

}
