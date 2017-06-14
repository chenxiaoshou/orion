package com.orion.manage.service.mysql.auth;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.MapPrivilegeMenu;

public interface MapPrivilegeMenuService {

	MapPrivilegeMenu save(MapPrivilegeMenu mapPrivilegeMenu);

	void delete(MapPrivilegeMenu mapPrivilegeMenu);

	MapPrivilegeMenu modify(MapPrivilegeMenu mapPrivilegeMenu) throws ApiException;

	MapPrivilegeMenu find(String mapPrivilegeMenuId);

	List<MapPrivilegeMenu> list();

	void save(Collection<MapPrivilegeMenu> mapPrivilegeMenus);

	void deleteInBatch(Collection<MapPrivilegeMenu> mapPrivilegeMenus);

}
