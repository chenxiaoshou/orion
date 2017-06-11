package com.orion.manage.service.mysql.auth;

import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.MapPrivilegeFile;

public interface MapPrivilegeFileService {

	MapPrivilegeFile save(MapPrivilegeFile mapPrivilegeFile);

	void delete(MapPrivilegeFile mapPrivilegeFile);

	MapPrivilegeFile modify(MapPrivilegeFile mapPrivilegeFile) throws ApiException;

	MapPrivilegeFile find(String mapPrivilegeFileId);

	List<MapPrivilegeFile> list();

}
