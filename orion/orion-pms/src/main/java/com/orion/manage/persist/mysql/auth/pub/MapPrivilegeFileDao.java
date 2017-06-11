package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.MapPrivilegeFile;
import com.orion.manage.persist.mysql.auth.custom.MapPrivilegeFileCustomDao;

public interface MapPrivilegeFileDao extends JpaRepository<MapPrivilegeFile, String>, MapPrivilegeFileCustomDao {

}
