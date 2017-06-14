package com.orion.manage.service.mysql.auth;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.MapRolePrivilege;

public interface MapRolePrivilegeService {

	MapRolePrivilege save(MapRolePrivilege mapRolePrivilege);

	void delete(MapRolePrivilege mapRolePrivilege);

	MapRolePrivilege modify(MapRolePrivilege mapRolePrivilege) throws ApiException;

	MapRolePrivilege find(String mapRolePrivilegeId);

	List<MapRolePrivilege> list();

	void save(Collection<MapRolePrivilege> mapRolePrivileges);

	void deleteInBatch(Collection<MapRolePrivilege> mapRolePrivileges);

}
