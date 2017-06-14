package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.Role;
import com.orion.manage.persist.mysql.auth.pub.RoleDao;
import com.orion.manage.service.mysql.auth.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public Role save(Role role) {
		if (role != null) {
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
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + role.toString() + "]");
		}
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

	@Override
	public List<Role> findByEnableTrueAndNameIn(List<String> roles) {
		return this.roleDao.findByEnableTrueAndNameIn(roles);
	}

	@Override
	public void save(Collection<Role> roles) {
		if (CollectionUtils.isNotEmpty(roles)) {
			this.roleDao.save(roles);
		}
	}

	@Override
	public void deleteInBatch(Collection<Role> roles) {
		if (CollectionUtils.isNotEmpty(roles)) {
			this.roleDao.deleteInBatch(roles);
		}
	}

}
