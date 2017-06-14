package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.MapPrivilegeMenu;
import com.orion.manage.persist.mysql.auth.pub.MapPrivilegeMenuDao;
import com.orion.manage.service.mysql.auth.MapPrivilegeMenuService;

@Service("mapPrivilegeMenuService")
public class MapPrivilegeMenuServiceImpl implements MapPrivilegeMenuService {

	@Autowired
	private MapPrivilegeMenuDao mapPrivilegeMenuDao;

	@Override
	public MapPrivilegeMenu save(MapPrivilegeMenu mapPrivilegeMenu) {
		if (mapPrivilegeMenu != null) {
			return this.mapPrivilegeMenuDao.save(mapPrivilegeMenu);
		}
		return null;
	}

	@Override
	public void delete(MapPrivilegeMenu mapPrivilegeMenu) {
		if (mapPrivilegeMenu != null) {
			this.mapPrivilegeMenuDao.delete(mapPrivilegeMenu);
		}
	}

	@Override
	public MapPrivilegeMenu modify(MapPrivilegeMenu mapPrivilegeMenu) throws ApiException {
		if (mapPrivilegeMenu == null) {
			return mapPrivilegeMenu;
		}
		if (StringUtils.isBlank(mapPrivilegeMenu.getId())) {
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapPrivilegeMenu.toString() + "]");
		}
		return this.mapPrivilegeMenuDao.save(mapPrivilegeMenu);
	}

	@Override
	public MapPrivilegeMenu find(String mapPrivilegeMenuId) {
		return this.mapPrivilegeMenuDao.findOne(mapPrivilegeMenuId);
	}

	@Override
	public List<MapPrivilegeMenu> list() {
		return this.mapPrivilegeMenuDao.findAll();
	}

	@Override
	public void save(Collection<MapPrivilegeMenu> mapPrivilegeMenus) {
		if (CollectionUtils.isNotEmpty(mapPrivilegeMenus)) {
			this.mapPrivilegeMenuDao.save(mapPrivilegeMenus);
		}
	}

	@Override
	public void deleteInBatch(Collection<MapPrivilegeMenu> mapPrivilegeMenus) {
		if (CollectionUtils.isNotEmpty(mapPrivilegeMenus)) {
			this.mapPrivilegeMenuDao.deleteInBatch(mapPrivilegeMenus);
		}
	}

}
