package com.polaris.manage.service.mysql.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.User;

public interface UserService {

	User save(User user);

	void delete(User user);

	User modify(User user) throws ApiException;

	User find(String userId);

	List<User> list();

	User findByUsername(String username);

}
