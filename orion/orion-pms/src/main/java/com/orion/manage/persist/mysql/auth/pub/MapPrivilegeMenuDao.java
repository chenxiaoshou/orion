package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.MapPrivilegeMenu;
import com.orion.manage.persist.mysql.auth.custom.MapPrivilegeMenuCustomDao;

public interface MapPrivilegeMenuDao extends JpaRepository<MapPrivilegeMenu, String>, MapPrivilegeMenuCustomDao {

}
