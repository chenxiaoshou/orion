package com.polaris.manage.service.mysql.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.MapPrivilegeFile;

public interface MapPrivilegeFileService {

	MapPrivilegeFile save(MapPrivilegeFile mapPrivilegeFile);

	void delete(MapPrivilegeFile mapPrivilegeFile);

	MapPrivilegeFile modify(MapPrivilegeFile mapPrivilegeFile) throws ApiException;

	MapPrivilegeFile find(String mapPrivilegeFileId);

	List<MapPrivilegeFile> list();

}
