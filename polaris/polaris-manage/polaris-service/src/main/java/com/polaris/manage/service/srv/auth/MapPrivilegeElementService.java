package com.polaris.manage.service.srv.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.MapPrivilegeElement;

public interface MapPrivilegeElementService {

	MapPrivilegeElement save(MapPrivilegeElement mapPrivilegeElement);

	void delete(MapPrivilegeElement mapPrivilegeElement);

	MapPrivilegeElement modify(MapPrivilegeElement mapPrivilegeElement) throws ApiException;

	MapPrivilegeElement find(String mapPrivilegeElementId);

	List<MapPrivilegeElement> list();

}
