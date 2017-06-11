package com.orion.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orion.manage.model.mysql.auth.Menu;
import com.orion.manage.persist.mysql.auth.custom.MenuCustomDao;

public interface MenuDao extends JpaRepository<Menu, String>, MenuCustomDao {

}
