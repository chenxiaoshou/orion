package com.orion.manage.service.mysql.auth;

import java.util.Collection;
import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.Element;

public interface ElementService {

	Element save(Element element);

	void delete(Element element);
	
	Element modify(Element element) throws ApiException;

	Element find(String elementId);

	List<Element> list();
	
	void save(Collection<Element> elements);

	void deleteInBatch(Collection<Element> elements);
}
