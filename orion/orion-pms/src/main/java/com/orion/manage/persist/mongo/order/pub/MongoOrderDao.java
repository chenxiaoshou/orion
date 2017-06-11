package com.orion.manage.persist.mongo.order.pub;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orion.manage.model.mysql.order.Order;

public interface MongoOrderDao extends MongoRepository<Order, String> {

}
