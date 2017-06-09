package com.polaris.manage.service.mysql.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.Role;

public interface RoleService {

	Role save(Role role);

	void delete(Role role);

	Role modify(Role role) throws ApiException;

	Role find(String roleId);

	List<Role> list();

	List<Role> findByIdIn(List<String> roleIds);

}
