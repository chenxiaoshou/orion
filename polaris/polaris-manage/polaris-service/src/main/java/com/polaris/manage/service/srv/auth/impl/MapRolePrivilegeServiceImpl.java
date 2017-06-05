package com.polaris.manage.service.srv.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.MapRolePrivilege;
import com.polaris.manage.persist.mysql.auth.pub.MapRolePrivilegeDao;
import com.polaris.manage.service.srv.auth.MapRolePrivilegeService;

@Service
public class MapRolePrivilegeServiceImpl implements MapRolePrivilegeService {

	@Autowired
	private MapRolePrivilegeDao mapRolePrivilegeDao;

	@Override
	public MapRolePrivilege save(MapRolePrivilege mapRolePrivilege) {
		if (mapRolePrivilege != null) {
			mapRolePrivilege.setUpdateTime(DateUtil.timestamp());
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
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapRolePrivilege.toString() + "]");
		}
		mapRolePrivilege.setUpdateTime(DateUtil.timestamp());
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

}
