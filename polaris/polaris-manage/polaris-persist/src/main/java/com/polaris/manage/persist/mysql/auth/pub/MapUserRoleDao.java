package com.polaris.manage.persist.mysql.auth.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.MapUserRole;
import com.polaris.manage.persist.mysql.auth.custom.MapUserRoleCustomDao;

public interface MapUserRoleDao extends JpaRepository<MapUserRole, String>, MapUserRoleCustomDao {

	List<MapUserRole> findByUserId(String userId);
	
	List<MapUserRole> findByRoleId(String roleId);
	
	MapUserRole findByUserIdAndRoleId(String userId, String roleId);

}
