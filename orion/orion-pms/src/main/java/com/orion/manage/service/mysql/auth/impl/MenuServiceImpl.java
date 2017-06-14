package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.Menu;
import com.orion.manage.persist.mysql.auth.pub.MenuDao;
import com.orion.manage.service.mysql.auth.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public Menu save(Menu menu) {
		if (menu != null) {
			return this.menuDao.save(menu);
		}
		return null;
	}

	@Override
	public void delete(Menu menu) {
		if (menu != null) {
			this.menuDao.delete(menu);
		}
	}

	@Override
	public Menu modify(Menu menu) throws ApiException {
		if (menu == null) {
			return menu;
		}
		if (StringUtils.isBlank(menu.getId())) {
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + menu.toString() + "]");
		}
		return this.menuDao.save(menu);
	}

	@Override
	public Menu find(String menuId) {
		return this.menuDao.findOne(menuId);
	}

	@Override
	public List<Menu> list() {
		return this.menuDao.findAll();
	}

	@Override
	public void save(Collection<Menu> menus) {
		if (CollectionUtils.isNotEmpty(menus)) {
			this.menuDao.save(menus);
		}
	}

	@Override
	public void deleteInBatch(Collection<Menu> menus) {
		if (CollectionUtils.isNotEmpty(menus)) {
			this.menuDao.deleteInBatch(menus);
		}
	}

}
