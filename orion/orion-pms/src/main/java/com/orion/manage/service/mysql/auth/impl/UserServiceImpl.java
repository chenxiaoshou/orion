package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.User;
import com.orion.manage.persist.mysql.auth.pub.UserDao;
import com.orion.manage.service.mysql.auth.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User save(User user) {
		if (user != null) {
			return this.userDao.save(user);
		}
		return null;
	}

	@Override
	public void delete(User user) {
		if (user != null) {
			this.userDao.delete(user);
		}
	}

	@Override
	public User modify(User user) throws ApiException {
		if (user == null) {
			return user;
		}
		if (StringUtils.isBlank(user.getId())) {
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + user.toString() + "]");
		}
		return this.userDao.save(user);
	}

	@Override
	public User find(String userId) {
		return this.userDao.findOne(userId);
	}

	@Override
	public List<User> list() {
		return this.userDao.findAll();
	}

	@Override
	public User findByUsernameAndEnableTrueAndLockedFalse(String username) {
		return this.userDao.findByUsernameAndEnableTrueAndLockedFalse(username);
	}

	@Override
	public User finaByUsername(String username) {
		return this.userDao.findByUsername(username);
	}

	@Override
	public User findByUsername(String username) {
		return this.userDao.findByUsername(username);
	}

	@Override
	public void save(Collection<User> users) {
		if (CollectionUtils.isNotEmpty(users)) {
			this.userDao.save(users);
		}
	}

	@Override
	public void deleteInBatch(Collection<User> users) {
		if (CollectionUtils.isNotEmpty(users)) {
			this.userDao.deleteInBatch(users);
		}
	}

}
