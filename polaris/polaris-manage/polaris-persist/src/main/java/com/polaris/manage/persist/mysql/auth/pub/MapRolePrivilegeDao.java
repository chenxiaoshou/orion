package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.MapRolePrivilege;
import com.polaris.manage.persist.mysql.auth.custom.MapRolePrivilegeCustomDao;

public interface MapRolePrivilegeDao extends JpaRepository<MapRolePrivilege, String>, MapRolePrivilegeCustomDao {

}
