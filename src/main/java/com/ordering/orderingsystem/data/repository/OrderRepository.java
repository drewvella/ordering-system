package com.ordering.orderingsystem.data.repository;

import com.ordering.orderingsystem.data.model.Order;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID>, QuerydslPredicateExecutor<Order> {

}
