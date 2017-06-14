package com.orion.manage.service.mysql.auth;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.Privilege;

public interface PrivilegeService {

	Privilege save(Privilege privilege);

	void delete(Privilege privilege);

	Privilege modify(Privilege privilege) throws ApiException;

	Privilege find(String privilegeId);

	List<Privilege> list();

	void save(Collection<Privilege> privileges);

	void deleteInBatch(Collection<Privilege> privileges);

}
