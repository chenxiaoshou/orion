package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.Privilege;
import com.orion.manage.persist.mysql.auth.custom.PrivilegeCustomDao;

public interface PrivilegeDao extends JpaRepository<Privilege, String>, PrivilegeCustomDao {

}
