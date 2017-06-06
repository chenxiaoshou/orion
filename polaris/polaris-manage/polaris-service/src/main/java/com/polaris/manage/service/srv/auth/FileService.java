package com.polaris.manage.service.srv.auth;

import java.util.List;

import com.polaris.common.exception.ApiException;
import com.polaris.manage.model.mysql.auth.File;

public interface FileService {

	File save(File file);

	void delete(File file);

	File modify(File file) throws ApiException;

	File find(String fileId);

	List<File> list();

}
