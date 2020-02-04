package com.ordering.orderingsystem.service.impl;

import com.google.common.collect.Lists;
import com.ordering.orderingsystem.api.dto.ModelMapper;
import com.ordering.orderingsystem.data.model.Order;
import com.ordering.orderingsystem.data.model.OrderProduct;
import com.ordering.orderingsystem.data.model.Product;
import com.ordering.orderingsystem.data.model.QOrder;
import com.ordering.orderingsystem.data.repository.OrderRepository;
import com.ordering.orderingsystem.data.repository.ProductRepository;
import com.ordering.orderingsystem.service.ManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ManagementServiceImpl implements ManagementService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UUID productId, Product newProduct) {
        //Retrieve old product, throw exception if it doesnt exist, update details and persist
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product does not exist");
        }
        Product product = existingProduct.get();
        product.setPrice(newProduct.getPrice());
        product.setProductName(newProduct.getProductName());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return Lists.newArrayList(productRepository.findAll());
    }

    @Override
    public Order createOrder(String email, List<UUID> productIds) {
        Order order = new Order();
        order.setBuyerEmail(email);

        List<Product> products = Lists.newArrayList(productRepository.findAllById(productIds));
        if (products.size() != productIds.size()) {
            throw new IllegalArgumentException("Invalid product id's specified");
        }

        //Map the products into OrderProduct objects to maintain history of their original name/price
        List<OrderProduct> orderProducts = modelMapper.mapAsList(products, OrderProduct.class);
        BigInteger total = BigInteger.ZERO;
        for (OrderProduct orderProduct : orderProducts) {
            total = total.add(orderProduct.getProductPrice());
        }
        order.setTotal(total);
        order.addOrderProducts(orderProducts);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders(OffsetDateTime startDate, OffsetDateTime endDate) {
        return Lists.newArrayList(orderRepository.findAll(QOrder.order.createdDateTime.between(startDate, endDate)));
    }

    @Override
    public Order findOrderById(UUID orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Invalid product id's specified");
        }
        return order.get();
    }
}
