package com.orion.manage.service.mysql.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.Privilege;
import com.orion.manage.persist.mysql.auth.pub.PrivilegeDao;
import com.orion.manage.service.mysql.auth.PrivilegeService;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;

	@Override
	public Privilege save(Privilege privilege) {
		if (privilege != null) {
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
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + privilege.toString() + "]");
		}
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
