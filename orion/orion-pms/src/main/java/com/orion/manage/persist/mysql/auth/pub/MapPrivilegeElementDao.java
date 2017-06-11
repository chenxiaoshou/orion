package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.MapPrivilegeElement;
import com.orion.manage.persist.mysql.auth.custom.MapPrivilegeElementCustomDao;

public interface MapPrivilegeElementDao extends JpaRepository<MapPrivilegeElement, String>, MapPrivilegeElementCustomDao {

}
