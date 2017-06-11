package com.orion.manage.persist.mysql.auth.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.Role;
import com.orion.manage.persist.mysql.auth.custom.RoleCustomDao;

public interface RoleDao extends JpaRepository<Role, String>, RoleCustomDao {

	List<Role> findByIdIn(List<String> roleIds);

}
