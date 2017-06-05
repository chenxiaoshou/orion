package com.polaris.manage.service.srv.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.MapUserRole;
import com.polaris.manage.persist.mysql.auth.pub.MapUserRoleDao;
import com.polaris.manage.service.srv.auth.MapUserRoleService;

@Service
public class MapUserRoleServiceImpl implements MapUserRoleService {

	@Autowired
	private MapUserRoleDao mapUserRoleDao;

	@Override
	public MapUserRole save(MapUserRole mapUserRole) {
		if (mapUserRole != null) {
			mapUserRole.setUpdateTime(DateUtil.timestamp());
			return this.mapUserRoleDao.save(mapUserRole);
		}
		return null;
	}

	@Override
	public void delete(MapUserRole mapUserRole) {
		if (mapUserRole != null) {
			this.mapUserRoleDao.delete(mapUserRole);
		}
	}

	@Override
	public MapUserRole modify(MapUserRole mapUserRole) throws ApiException {
		if (mapUserRole == null) {
			return mapUserRole;
		}
		if (StringUtils.isBlank(mapUserRole.getId())) {
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapUserRole.toString() + "]");
		}
		mapUserRole.setUpdateTime(DateUtil.timestamp());
		return this.mapUserRoleDao.save(mapUserRole);
	}

	@Override
	public MapUserRole find(String mapUserRoleId) {
		return this.mapUserRoleDao.findOne(mapUserRoleId);
	}

	@Override
	public List<MapUserRole> list() {
		return this.mapUserRoleDao.findAll();
	}

}
