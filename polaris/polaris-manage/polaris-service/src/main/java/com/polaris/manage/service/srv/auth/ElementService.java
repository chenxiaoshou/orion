package com.polaris.manage.service.srv.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.Element;

public interface ElementService {

	Element save(Element element);

	void delete(Element element);

	Element modify(Element element) throws ApiException;

	Element find(String elementId);

	List<Element> list();

}
