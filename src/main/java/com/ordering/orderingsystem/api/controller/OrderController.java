package com.ordering.orderingsystem.api.controller;

import com.ordering.orderingsystem.api.dto.ApiOrder;
import com.ordering.orderingsystem.api.dto.ApiOrderRequest;
import com.ordering.orderingsystem.api.dto.ApiProduct;
import com.ordering.orderingsystem.api.dto.ModelMapper;
import com.ordering.orderingsystem.data.model.Order;
import com.ordering.orderingsystem.service.ManagementService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrderController {

    private final ModelMapper modelMapper;
    private final ManagementService managementService;


    @PostMapping("/orders")
    public ApiOrder createOrder(@RequestBody ApiOrderRequest orderRequest) {
        return modelMapper.map(managementService.createOrder(orderRequest.getBuyerEmail(), orderRequest.getProducts()), ApiOrder.class);
    }

    @GetMapping("/orders")
    public List<ApiOrder> getOrders(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate) {
        return modelMapper.mapAsList(managementService.getOrders(startDate, endDate), ApiOrder.class);
    }

    @GetMapping("/orders/{orderId}/products")
    public List<ApiProduct> getOrderProducts(@PathVariable UUID orderId) {
        Order order = managementService.findOrderById(orderId);
        return modelMapper.mapAsList(order.getProducts(), ApiProduct.class);
    }

}
