package com.polaris.manage.service.srv.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.Operation;
import com.polaris.manage.persist.mysql.auth.pub.OperationDao;
import com.polaris.manage.service.srv.auth.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationDao operationDao;

	@Override
	public Operation save(Operation operation) {
		if (operation != null) {
			operation.setUpdateTime(DateUtil.timestamp());
			return this.operationDao.save(operation);
		}
		return null;
	}

	@Override
	public void delete(Operation operation) {
		if (operation != null) {
			this.operationDao.delete(operation);
		}
	}

	@Override
	public Operation modify(Operation operation) throws ApiException {
		if (operation == null) {
			return operation;
		}
		if (StringUtils.isBlank(operation.getId())) {
			throw new PolarisException("目标对象的ID字段为空，无法执行数据库修改操作！[" + operation.toString() + "]");
		}
		operation.setUpdateTime(DateUtil.timestamp());
		return this.operationDao.save(operation);
	}

	@Override
	public Operation find(String operationId) {
		return this.operationDao.findOne(operationId);
	}

	@Override
	public List<Operation> list() {
		return this.operationDao.findAll();
	}

}
