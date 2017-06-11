package com.orion.manage.service.mysql.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.common.exception.ApiException;
import com.orion.common.exception.AppException;
import com.orion.manage.model.mysql.auth.Operation;
import com.orion.manage.persist.mysql.auth.pub.OperationDao;
import com.orion.manage.service.mysql.auth.OperationService;

@Service("operationService")
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationDao operationDao;

	@Override
	public Operation save(Operation operation) {
		if (operation != null) {
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
			throw new AppException("目标对象的ID字段为空，无法执行数据库修改操作！[" + operation.toString() + "]");
		}
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
