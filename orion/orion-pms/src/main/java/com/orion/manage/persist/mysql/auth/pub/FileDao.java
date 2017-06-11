package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.File;
import com.orion.manage.persist.mysql.auth.custom.FileCustomDao;

public interface FileDao extends JpaRepository<File, String>, FileCustomDao {

}
