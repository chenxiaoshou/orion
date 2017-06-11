package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.MapRolePrivilege;
import com.orion.manage.persist.mysql.auth.custom.MapRolePrivilegeCustomDao;

public interface MapRolePrivilegeDao extends JpaRepository<MapRolePrivilege, String>, MapRolePrivilegeCustomDao {

}
