package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.MapPrivilegeElement;
import com.orion.manage.persist.mysql.auth.pub.MapPrivilegeElementDao;
import com.orion.manage.service.mysql.auth.MapPrivilegeElementService;

@Service("mapPrivilegeElementService")
public class MapPrivilegeElementServiceImpl implements MapPrivilegeElementService {

	@Autowired
	private MapPrivilegeElementDao mapPrivilegeElementDao;

	@Override
	public MapPrivilegeElement save(MapPrivilegeElement mapPrivilegeElement) {
		if (mapPrivilegeElement != null) {
			return this.mapPrivilegeElementDao.save(mapPrivilegeElement);
		}
		return null;
	}

	@Override
	public void delete(MapPrivilegeElement mapPrivilegeElement) {
		if (mapPrivilegeElement != null) {
			this.mapPrivilegeElementDao.delete(mapPrivilegeElement);
		}
	}

	@Override
	public MapPrivilegeElement modify(MapPrivilegeElement mapPrivilegeElement) throws ApiException {
		if (mapPrivilegeElement == null) {
			return mapPrivilegeElement;
		}
		if (StringUtils.isBlank(mapPrivilegeElement.getId())) {
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapPrivilegeElement.toString() + "]");
		}
		return this.mapPrivilegeElementDao.save(mapPrivilegeElement);
	}

	@Override
	public MapPrivilegeElement find(String mapPrivilegeElementId) {
		return this.mapPrivilegeElementDao.findOne(mapPrivilegeElementId);
	}

	@Override
	public List<MapPrivilegeElement> list() {
		return this.mapPrivilegeElementDao.findAll();
	}

	@Override
	public void save(Collection<MapPrivilegeElement> mapPrivilegeElements) {
		if (CollectionUtils.isNotEmpty(mapPrivilegeElements)) {
			this.mapPrivilegeElementDao.save(mapPrivilegeElements);
		}
	}

	@Override
	public void deleteInBatch(Collection<MapPrivilegeElement> mapPrivilegeElements) {
		if (CollectionUtils.isNotEmpty(mapPrivilegeElements)) {
			this.mapPrivilegeElementDao.deleteInBatch(mapPrivilegeElements);
		}
	}

}
