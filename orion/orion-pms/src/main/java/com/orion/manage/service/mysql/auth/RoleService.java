package com.orion.manage.service.mysql.auth;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.Role;

public interface RoleService {

	Role save(Role role);

	void delete(Role role);

	Role modify(Role role) throws ApiException;

	Role find(String roleId);

	List<Role> list();

	List<Role> findByIdIn(List<String> roleIds);

	List<Role> findByEnableTrueAndNameIn(List<String> roles);

	void save(Collection<Role> roles);

	void deleteInBatch(Collection<Role> roles);

}
