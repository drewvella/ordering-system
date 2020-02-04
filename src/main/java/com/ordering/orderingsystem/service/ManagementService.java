package com.ordering.orderingsystem.service;

import com.ordering.orderingsystem.data.model.Order;
import com.ordering.orderingsystem.data.model.Product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface ManagementService {

    Product createProduct(Product product);

    Product updateProduct(UUID productId, Product newProduct);

    List<Product> getProducts();

    Order createOrder(String email, List<UUID> productIds);

    List<Order> getOrders(OffsetDateTime startDate, OffsetDateTime endDate);

    Order findOrderById(UUID orderId);
}
