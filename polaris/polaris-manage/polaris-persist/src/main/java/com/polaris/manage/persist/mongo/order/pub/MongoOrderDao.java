package com.polaris.manage.persist.mongo.order.pub;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.polaris.manage.model.mysql.order.Order;

public interface MongoOrderDao extends MongoRepository<Order, String> {

}
