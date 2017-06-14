package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.MapUserRole;
import com.orion.manage.persist.mysql.auth.pub.MapUserRoleDao;
import com.orion.manage.service.mysql.auth.MapUserRoleService;

@Service("mapUserRoleService")
public class MapUserRoleServiceImpl implements MapUserRoleService {

	@Autowired
	private MapUserRoleDao mapUserRoleDao;

	@Override
	public MapUserRole save(MapUserRole mapUserRole) {
		if (mapUserRole != null) {
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
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapUserRole.toString() + "]");
		}
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

	@Override
	public List<MapUserRole> findByUserId(String userId) {
		return this.mapUserRoleDao.findByUserId(userId);
	}

	@Override
	public List<MapUserRole> findByRoleId(String roleId) {
		return this.mapUserRoleDao.findByRoleId(roleId);
	}

	@Override
	public MapUserRole findByUserIdAndRoleId(String userId, String roleId) {
		return this.mapUserRoleDao.findByUserIdAndRoleId(userId, roleId);
	}

	@Override
	public void save(Collection<MapUserRole> mapUserRoles) {
		if (CollectionUtils.isNotEmpty(mapUserRoles)) {
			this.mapUserRoleDao.save(mapUserRoles);
		}
	}

	@Override
	public void deleteInBatch(Collection<MapUserRole> mapUserRoles) {
		if (CollectionUtils.isNotEmpty(mapUserRoles)) {
			this.mapUserRoleDao.deleteInBatch(mapUserRoles);
		}
	}

}
