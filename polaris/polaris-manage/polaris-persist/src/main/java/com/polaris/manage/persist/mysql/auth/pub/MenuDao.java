package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.Menu;
import com.polaris.manage.persist.mysql.auth.custom.MenuCustomDao;

public interface MenuDao extends JpaRepository<Menu, String>, MenuCustomDao {

}
