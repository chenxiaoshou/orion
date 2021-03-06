package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.MapRolePrivilege;
import com.orion.manage.persist.mysql.auth.pub.MapRolePrivilegeDao;
import com.orion.manage.service.mysql.auth.MapRolePrivilegeService;

@Service("mapRolePrivilegeService")
public class MapRolePrivilegeServiceImpl implements MapRolePrivilegeService {

	@Autowired
	private MapRolePrivilegeDao mapRolePrivilegeDao;

	@Override
	public MapRolePrivilege save(MapRolePrivilege mapRolePrivilege) {
		if (mapRolePrivilege != null) {
			return this.mapRolePrivilegeDao.save(mapRolePrivilege);
		}
		return null;
	}

	@Override
	public void delete(MapRolePrivilege mapRolePrivilege) {
		if (mapRolePrivilege != null) {
			this.mapRolePrivilegeDao.delete(mapRolePrivilege);
		}
	}

	@Override
	public MapRolePrivilege modify(MapRolePrivilege mapRolePrivilege) throws ApiException {
		if (mapRolePrivilege == null) {
			return mapRolePrivilege;
		}
		if (StringUtils.isBlank(mapRolePrivilege.getId())) {
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapRolePrivilege.toString() + "]");
		}
		return this.mapRolePrivilegeDao.save(mapRolePrivilege);
	}

	@Override
	public MapRolePrivilege find(String mapRolePrivilegeId) {
		return this.mapRolePrivilegeDao.findOne(mapRolePrivilegeId);
	}

	@Override
	public List<MapRolePrivilege> list() {
		return this.mapRolePrivilegeDao.findAll();
	}

	@Override
	public void save(Collection<MapRolePrivilege> mapRolePrivileges) {
		if (CollectionUtils.isNotEmpty(mapRolePrivileges)) {
			this.mapRolePrivilegeDao.save(mapRolePrivileges);
		}
	}

	@Override
	public void deleteInBatch(Collection<MapRolePrivilege> mapRolePrivileges) {
		if (CollectionUtils.isNotEmpty(mapRolePrivileges)) {
			this.mapRolePrivilegeDao.deleteInBatch(mapRolePrivileges);
		}
	}

}
