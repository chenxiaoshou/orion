package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.Element;
import com.orion.manage.persist.mysql.auth.pub.ElementDao;
import com.orion.manage.service.mysql.auth.ElementService;

@Service("elementService")
public class ElementServiceImpl implements ElementService {

	@Autowired
	private ElementDao elementDao;

	@Override
	public Element save(Element element) {
		if (element != null) {
			return this.elementDao.save(element);
		}
		return null;
	}

	@Override
	public void delete(Element element) {
		if (element != null) {
			this.elementDao.delete(element);
		}
	}

	@Override
	public Element modify(Element element) throws ApiException {
		if (element == null) {
			return element;
		}
		if (StringUtils.isBlank(element.getId())) {
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + element.toString() + "]");
		}
		return this.elementDao.save(element);
	}

	@Override
	public Element find(String elementId) {
		return this.elementDao.findOne(elementId);
	}

	@Override
	public List<Element> list() {
		return this.elementDao.findAll();
	}

	@Override
	public void save(Collection<Element> elements) {
		if (CollectionUtils.isNotEmpty(elements)) {
			this.elementDao.save(elements);
		}
	}

	@Override
	public void deleteInBatch(Collection<Element> elements) {
		if (CollectionUtils.isNotEmpty(elements)) {
			this.elementDao.deleteInBatch(elements);
		}
	}

}
