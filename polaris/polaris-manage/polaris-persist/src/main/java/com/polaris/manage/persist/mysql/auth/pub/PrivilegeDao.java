package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.Privilege;
import com.polaris.manage.persist.mysql.auth.custom.PrivilegeCustomDao;

public interface PrivilegeDao extends JpaRepository<Privilege, String>, PrivilegeCustomDao {

}
