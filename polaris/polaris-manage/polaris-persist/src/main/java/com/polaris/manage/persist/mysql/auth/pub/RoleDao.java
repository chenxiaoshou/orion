package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.Role;
import com.polaris.manage.persist.mysql.auth.custom.RoleCustomDao;

public interface RoleDao extends JpaRepository<Role, String>, RoleCustomDao {

}
