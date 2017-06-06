package com.polaris.manage.service.srv.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.Role;
import com.polaris.manage.persist.mysql.auth.pub.RoleDao;
import com.polaris.manage.service.srv.auth.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public Role save(Role role) {
		if (role != null) {
			role.setUpdateTime(DateUtil.timestamp());
			return this.roleDao.save(role);
		}
		return null;
	}

	@Override
	public void delete(Role role) {
		if (role != null) {
			this.roleDao.delete(role);
		}
	}

	@Override
	public Role modify(Role role) throws ApiException {
		if (role == null) {
			return role;
		}
		if (StringUtils.isBlank(role.getId())) {
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + role.toString() + "]");
		}
		role.setUpdateTime(DateUtil.timestamp());
		return this.roleDao.save(role);
	}

	@Override
	public Role find(String roleId) {
		return this.roleDao.findOne(roleId);
	}

	@Override
	public List<Role> list() {
		return this.roleDao.findAll();
	}

	@Override
	public List<Role> findByIdIn(List<String> roleIds) {
		return this.roleDao.findByIdIn(roleIds);
	}

}
