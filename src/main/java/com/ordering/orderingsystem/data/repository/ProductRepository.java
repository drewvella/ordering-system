package com.ordering.orderingsystem.data.repository;

import com.ordering.orderingsystem.data.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}
