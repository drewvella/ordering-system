package com.ordering.orderingsystem.api.controller;

import com.ordering.orderingsystem.api.dto.ApiProduct;
import com.ordering.orderingsystem.api.dto.ModelMapper;
import com.ordering.orderingsystem.data.model.Product;
import com.ordering.orderingsystem.service.ManagementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ModelMapper modelMapper;
    private final ManagementService managementService;

    @PostMapping("/products")
    public ApiProduct createProduct(@RequestBody ApiProduct productToCreate) {
        Product product = modelMapper.map(productToCreate, Product.class);
        return modelMapper.map(managementService.createProduct(product), ApiProduct.class);
    }

    @PutMapping("/products/{productId}")
    public ApiProduct updateProduct(@PathVariable UUID productId, @RequestBody ApiProduct productToUpdate) {
        Product updatedProduct = modelMapper.map(productToUpdate, Product.class);
        return modelMapper.map(managementService.updateProduct(productId, updatedProduct), ApiProduct.class);
    }


    @GetMapping("/products")
    public List<ApiProduct> getProducts() {
        return modelMapper.mapAsList(managementService.getProducts(), ApiProduct.class);
    }

}
