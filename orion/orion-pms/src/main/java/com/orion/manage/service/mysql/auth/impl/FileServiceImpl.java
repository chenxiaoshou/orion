package com.orion.manage.service.mysql.auth.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.File;
import com.orion.manage.persist.mysql.auth.pub.FileDao;
import com.orion.manage.service.mysql.auth.FileService;

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
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + file.toString() + "]");
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

	@Override
	public void save(Collection<File> files) {
		if (CollectionUtils.isNotEmpty(files)) {
			this.fileDao.save(files);
		}
	}

	@Override
	public void deleteInBatch(Collection<File> files) {
		if (CollectionUtils.isNotEmpty(files)) {
			this.fileDao.deleteInBatch(files);
		}
	}

}
