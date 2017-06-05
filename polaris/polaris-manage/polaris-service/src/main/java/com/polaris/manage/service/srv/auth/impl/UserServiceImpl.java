package com.polaris.manage.service.srv.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.User;
import com.polaris.manage.persist.mysql.auth.pub.UserDao;
import com.polaris.manage.service.srv.auth.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User save(User user) {
		if (user != null) {
			user.setUpdateTime(DateUtil.timestamp());
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
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + user.toString() + "]");
		}
		user.setUpdateTime(DateUtil.timestamp());
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

}
