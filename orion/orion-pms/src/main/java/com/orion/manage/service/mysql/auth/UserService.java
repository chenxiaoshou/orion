package com.orion.manage.service.mysql.auth;

import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.User;

public interface UserService {

	User save(User user);

	void delete(User user);

	User modify(User user) throws ApiException;

	User find(String userId);

	List<User> list();

	User finaByUsername(String username);
	
	User findByUsernameAndEnableTrueAndLockedFalse(String username);

}
