package com.polaris.manage.service.mysql.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.Privilege;

public interface PrivilegeService {

	Privilege save(Privilege privilege);

	void delete(Privilege privilege);

	Privilege modify(Privilege privilege) throws ApiException;

	Privilege find(String privilegeId);

	List<Privilege> list();

}
