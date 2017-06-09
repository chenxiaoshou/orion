package com.polaris.manage.service.mysql.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.Menu;

public interface MenuService {

	Menu save(Menu menu);

	void delete(Menu menu);

	Menu modify(Menu menu) throws ApiException;

	Menu find(String menuId);

	List<Menu> list();

}
