package com.polaris.manage.service.mysql.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.MapPrivilegeMenu;

public interface MapPrivilegeMenuService {

	MapPrivilegeMenu save(MapPrivilegeMenu mapPrivilegeMenu);

	void delete(MapPrivilegeMenu mapPrivilegeMenu);

	MapPrivilegeMenu modify(MapPrivilegeMenu mapPrivilegeMenu) throws ApiException;

	MapPrivilegeMenu find(String mapPrivilegeMenuId);

	List<MapPrivilegeMenu> list();

}
