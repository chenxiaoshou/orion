package com.orion.manage.service.mysql.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.MapPrivilegeOperation;
import com.orion.manage.persist.mysql.auth.pub.MapPrivilegeOperationDao;
import com.orion.manage.service.mysql.auth.MapPrivilegeOperationService;

@Service("mapPrivilegeOperationService")
public class MapPrivilegeOperationServiceImpl implements MapPrivilegeOperationService {

	@Autowired
	private MapPrivilegeOperationDao mapPrivilegeOperationDao;

	@Override
	public MapPrivilegeOperation save(MapPrivilegeOperation mapPrivilegeOperation) {
		if (mapPrivilegeOperation != null) {
			return this.mapPrivilegeOperationDao.save(mapPrivilegeOperation);
		}
		return null;
	}

	@Override
	public void delete(MapPrivilegeOperation mapPrivilegeOperation) {
		if (mapPrivilegeOperation != null) {
			this.mapPrivilegeOperationDao.delete(mapPrivilegeOperation);
		}
	}

	@Override
	public MapPrivilegeOperation modify(MapPrivilegeOperation mapPrivilegeOperation) throws ApiException {
		if (mapPrivilegeOperation == null) {
			return mapPrivilegeOperation;
		}
		if (StringUtils.isBlank(mapPrivilegeOperation.getId())) {
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapPrivilegeOperation.toString() + "]");
		}
		return this.mapPrivilegeOperationDao.save(mapPrivilegeOperation);
	}

	@Override
	public MapPrivilegeOperation find(String mapPrivilegeOperationId) {
		return this.mapPrivilegeOperationDao.findOne(mapPrivilegeOperationId);
	}

	@Override
	public List<MapPrivilegeOperation> list() {
		return this.mapPrivilegeOperationDao.findAll();
	}

}
