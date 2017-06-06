package com.polaris.manage.service.srv.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.MapUserRole;

public interface MapUserRoleService {

	MapUserRole save(MapUserRole mapUserRole);

	void delete(MapUserRole mapUserRole);

	MapUserRole modify(MapUserRole mapUserRole) throws ApiException;

	MapUserRole find(String mapUserRoleId);

	List<MapUserRole> list();

	List<MapUserRole> findByUserId(String userId);
	
	List<MapUserRole> findByRoleId(String roleId);
	
	MapUserRole findByUserIdAndRoleId(String userId, String roleId);

}
