package com.orion.manage.service.mysql.auth;

import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.Menu;

public interface MenuService {

	Menu save(Menu menu);

	void delete(Menu menu);

	Menu modify(Menu menu) throws ApiException;

	Menu find(String menuId);

	List<Menu> list();

}
