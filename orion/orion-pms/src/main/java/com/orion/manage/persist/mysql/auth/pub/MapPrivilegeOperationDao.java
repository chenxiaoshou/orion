package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.MapPrivilegeOperation;
import com.orion.manage.persist.mysql.auth.custom.MapPrivilegeOperationCustomDao;

public interface MapPrivilegeOperationDao extends JpaRepository<MapPrivilegeOperation, String>, MapPrivilegeOperationCustomDao {

}
