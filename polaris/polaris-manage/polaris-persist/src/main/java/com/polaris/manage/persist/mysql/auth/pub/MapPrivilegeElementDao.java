package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.MapPrivilegeElement;
import com.polaris.manage.persist.mysql.auth.custom.MapPrivilegeElementCustomDao;

public interface MapPrivilegeElementDao extends JpaRepository<MapPrivilegeElement, String>, MapPrivilegeElementCustomDao {

}
