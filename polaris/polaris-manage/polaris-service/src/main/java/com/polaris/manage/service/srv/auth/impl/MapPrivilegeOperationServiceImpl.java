package com.polaris.manage.service.srv.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.MapPrivilegeOperation;
import com.polaris.manage.persist.mysql.auth.pub.MapPrivilegeOperationDao;
import com.polaris.manage.service.srv.auth.MapPrivilegeOperationService;

@Service
public class MapPrivilegeOperationServiceImpl implements MapPrivilegeOperationService {

	@Autowired
	private MapPrivilegeOperationDao mapPrivilegeOperationDao;

	@Override
	public MapPrivilegeOperation save(MapPrivilegeOperation mapPrivilegeOperation) {
		if (mapPrivilegeOperation != null) {
			mapPrivilegeOperation.setUpdateTime(DateUtil.timestamp());
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
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapPrivilegeOperation.toString() + "]");
		}
		mapPrivilegeOperation.setUpdateTime(DateUtil.timestamp());
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
