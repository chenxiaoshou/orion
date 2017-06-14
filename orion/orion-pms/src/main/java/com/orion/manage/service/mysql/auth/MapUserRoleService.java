package com.orion.manage.service.mysql.auth;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.MapUserRole;

public interface MapUserRoleService {

	MapUserRole save(MapUserRole mapUserRole);

	void delete(MapUserRole mapUserRole);

	MapUserRole modify(MapUserRole mapUserRole) throws ApiException;

	MapUserRole find(String mapUserRoleId);

	List<MapUserRole> list();

	List<MapUserRole> findByUserId(String userId);

	List<MapUserRole> findByRoleId(String roleId);

	MapUserRole findByUserIdAndRoleId(String userId, String roleId);

	void save(Collection<MapUserRole> mapUserRoles);

	void deleteInBatch(Collection<MapUserRole> mapUserRoles);

}
