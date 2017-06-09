package com.polaris.manage.service.mysql.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.manage.model.mysql.auth.File;
import com.polaris.manage.persist.mysql.auth.pub.FileDao;
import com.polaris.manage.service.mysql.auth.FileService;

@Service("fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private FileDao fileDao;

	@Override
	public File save(File file) {
		if (file != null) {
			return this.fileDao.save(file);
		}
		return null;
	}

	@Override
	public void delete(File file) {
		if (file != null) {
			this.fileDao.delete(file);
		}
	}

	@Override
	public File modify(File file) throws ApiException {
		if (file == null) {
			return file;
		}
		if (StringUtils.isBlank(file.getId())) {
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + file.toString() + "]");
		}
		return this.fileDao.save(file);
	}

	@Override
	public File find(String fileId) {
		return this.fileDao.findOne(fileId);
	}

	@Override
	public List<File> list() {
		return this.fileDao.findAll();
	}

}
