package com.polaris.manage.service.srv.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.Menu;
import com.polaris.manage.persist.mysql.auth.pub.MenuDao;
import com.polaris.manage.service.srv.auth.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public Menu save(Menu menu) {
		if (menu != null) {
			menu.setUpdateTime(DateUtil.timestamp());
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
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + menu.toString() + "]");
		}
		menu.setUpdateTime(DateUtil.timestamp());
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

}
