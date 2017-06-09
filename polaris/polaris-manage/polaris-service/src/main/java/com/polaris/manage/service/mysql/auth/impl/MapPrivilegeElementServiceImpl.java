package com.polaris.manage.service.mysql.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.manage.model.mysql.auth.MapPrivilegeElement;
import com.polaris.manage.persist.mysql.auth.pub.MapPrivilegeElementDao;
import com.polaris.manage.service.mysql.auth.MapPrivilegeElementService;

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
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + mapPrivilegeElement.toString() + "]");
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

}
