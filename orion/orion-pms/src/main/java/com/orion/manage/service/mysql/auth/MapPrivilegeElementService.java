package com.orion.manage.service.mysql.auth;

import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.MapPrivilegeElement;

public interface MapPrivilegeElementService {

	MapPrivilegeElement save(MapPrivilegeElement mapPrivilegeElement);

	void delete(MapPrivilegeElement mapPrivilegeElement);

	MapPrivilegeElement modify(MapPrivilegeElement mapPrivilegeElement) throws ApiException;

	MapPrivilegeElement find(String mapPrivilegeElementId);

	List<MapPrivilegeElement> list();

}
