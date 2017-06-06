package com.polaris.manage.persist.mysql.auth.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polaris.manage.model.mysql.auth.Operation;
import com.polaris.manage.persist.mysql.auth.custom.OperationCustomDao;

public interface OperationDao extends JpaRepository<Operation, String>, OperationCustomDao {

}
