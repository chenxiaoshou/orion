package com.polaris.manage.service.srv.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.Privilege;
import com.polaris.manage.persist.mysql.auth.pub.PrivilegeDao;
import com.polaris.manage.service.srv.auth.PrivilegeService;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;

	@Override
	public Privilege save(Privilege privilege) {
		if (privilege != null) {
			privilege.setUpdateTime(DateUtil.timestamp());
			return this.privilegeDao.save(privilege);
		}
		return null;
	}

	@Override
	public void delete(Privilege privilege) {
		if (privilege != null) {
			this.privilegeDao.delete(privilege);
		}
	}

	@Override
	public Privilege modify(Privilege privilege) throws ApiException {
		if (privilege == null) {
			return privilege;
		}
		if (StringUtils.isBlank(privilege.getId())) {
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + privilege.toString() + "]");
		}
		privilege.setUpdateTime(DateUtil.timestamp());
		return this.privilegeDao.save(privilege);
	}

	@Override
	public Privilege find(String privilegeId) {
		return this.privilegeDao.findOne(privilegeId);
	}

	@Override
	public List<Privilege> list() {
		return this.privilegeDao.findAll();
	}

}
