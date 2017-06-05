package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.File;
import com.polaris.manage.persist.mysql.auth.custom.FileCustomDao;

public interface FileDao extends JpaRepository<File, String>, FileCustomDao {

}
