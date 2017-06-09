package com.polaris.manage.service.mysql.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.MapRolePrivilege;

public interface MapRolePrivilegeService {

	MapRolePrivilege save(MapRolePrivilege mapRolePrivilege);

	void delete(MapRolePrivilege mapRolePrivilege);

	MapRolePrivilege modify(MapRolePrivilege mapRolePrivilege) throws ApiException;

	MapRolePrivilege find(String mapRolePrivilegeId);

	List<MapRolePrivilege> list();

}
