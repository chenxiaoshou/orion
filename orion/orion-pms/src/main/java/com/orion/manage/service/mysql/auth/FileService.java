package com.orion.manage.service.mysql.auth;

import java.util.List;

import com.orion.common.exception.ApiException;
import com.orion.manage.model.mysql.auth.File;

public interface FileService {

	File save(File file);

	void delete(File file);

	File modify(File file) throws ApiException;

	File find(String fileId);

	List<File> list();

}
