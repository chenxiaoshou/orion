package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.MapPrivilegeMenu;
import com.polaris.manage.persist.mysql.auth.custom.MapPrivilegeMenuCustomDao;

public interface MapPrivilegeMenuDao extends JpaRepository<MapPrivilegeMenu, String>, MapPrivilegeMenuCustomDao {

}
