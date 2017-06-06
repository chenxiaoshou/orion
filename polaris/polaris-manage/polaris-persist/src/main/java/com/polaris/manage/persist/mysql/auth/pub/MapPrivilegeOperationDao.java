package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.MapPrivilegeOperation;
import com.polaris.manage.persist.mysql.auth.custom.MapPrivilegeOperationCustomDao;

public interface MapPrivilegeOperationDao extends JpaRepository<MapPrivilegeOperation, String>, MapPrivilegeOperationCustomDao {

}
