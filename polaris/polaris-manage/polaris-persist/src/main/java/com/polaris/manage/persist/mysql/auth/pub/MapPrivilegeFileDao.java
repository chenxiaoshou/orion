package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.MapPrivilegeFile;
import com.polaris.manage.persist.mysql.auth.custom.MapPrivilegeFileCustomDao;

public interface MapPrivilegeFileDao extends JpaRepository<MapPrivilegeFile, String>, MapPrivilegeFileCustomDao {

}
