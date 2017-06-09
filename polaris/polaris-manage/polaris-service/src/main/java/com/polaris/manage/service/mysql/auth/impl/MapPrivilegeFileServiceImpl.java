package com.polaris.manage.service.mysql.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.manage.model.mysql.auth.MapPrivilegeFile;
import com.polaris.manage.persist.mysql.auth.pub.MapPrivilegeFileDao;
import com.polaris.manage.service.mysql.auth.MapPrivilegeFileService;

@Service("mapPrivilegeFileService")
public class MapPrivilegeFileServiceImpl implements MapPrivilegeFileService {

	@Autowired
	private MapPrivilegeFileDao mapPrivilegeFileDao;

	@Override
	public MapPrivilegeFile save(MapPrivilegeFile mapPrivilegeFile) {
		if (mapPrivilegeFile != null) {
			return this.mapPrivilegeFileDao.save(mapPrivilegeFile);
		}
		return null;
	}

	@Override
	public void delete(MapPrivilegeFile mapPrivilegeFile) {
		if (mapPrivilegeFile != null) {
			this.mapPrivilegeFileDao.delete(mapPrivilegeFile);
		}
	}

	@Override
	public MapPrivilegeFile modify(MapPrivilegeFile mapPrivilegeFile) throws ApiException {
		if (mapPrivilegeFile == null) {
			return mapPrivilegeFile;
		}
		if (StringUtils.isBlank(mapPrivilegeFile.getId())) {
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapPrivilegeFile.toString() + "]");
		}
		return this.mapPrivilegeFileDao.save(mapPrivilegeFile);
	}

	@Override
	public MapPrivilegeFile find(String mapPrivilegeFileId) {
		return this.mapPrivilegeFileDao.findOne(mapPrivilegeFileId);
	}

	@Override
	public List<MapPrivilegeFile> list() {
		return this.mapPrivilegeFileDao.findAll();
	}

}
