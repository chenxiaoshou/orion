package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.User;
import com.orion.manage.persist.mysql.auth.custom.UserCustomDao;

public interface UserDao extends JpaRepository<User, String>, UserCustomDao {

	User findByUsernameAndEnableTrueAndLockedFalse(String username);

	User findByUsername(String username);

}
