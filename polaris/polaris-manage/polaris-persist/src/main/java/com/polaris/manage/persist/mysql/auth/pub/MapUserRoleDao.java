package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.MapUserRole;
import com.polaris.manage.persist.mysql.auth.custom.MapUserRoleCustomDao;

public interface MapUserRoleDao extends JpaRepository<MapUserRole, String>, MapUserRoleCustomDao {

}
