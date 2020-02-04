package com.ordering.orderingsystem.service.impl;

import com.google.common.collect.Lists;
import com.ordering.orderingsystem.api.dto.ModelMapper;
import com.ordering.orderingsystem.data.model.Order;
import com.ordering.orderingsystem.data.model.Product;
import com.ordering.orderingsystem.data.repository.OrderRepository;
import com.ordering.orderingsystem.data.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ManagementServiceImplTest {


    private ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OrderRepository orderRepository;

    private ManagementServiceImpl managementService;

    @Before
    public void init() {
        managementService = new ManagementServiceImpl(productRepository, modelMapper, orderRepository);
    }

    @Test
    public void testProductSave() {
        Product product = new Product();
        product.setProductName("test");
        product.setPrice(new BigInteger("20"));
        managementService.createProduct(product);
        Mockito.verify(productRepository, Mockito.atMostOnce()).save(any(Product.class));
    }

    @Test
    public void testProductUpdate() {
        Product oldProduct = new Product();
        oldProduct.setProductName("test");
        oldProduct.setPrice(new BigInteger("20"));

        Product newProduct = new Product();
        newProduct.setProductName("test2");
        newProduct.setPrice(new BigInteger("40"));
        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(oldProduct));

        Product updatedProduct = new Product();
        updatedProduct.setId(oldProduct.getId());
        updatedProduct.setProductName(newProduct.getProductName());
        updatedProduct.setPrice(newProduct.getPrice());

        Mockito.when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        Product result = managementService.updateProduct(oldProduct.getId(), newProduct);
        Mockito.verify(productRepository, Mockito.atMostOnce()).findById(any(UUID.class));
        Mockito.verify(productRepository, Mockito.atMostOnce()).save(any(Product.class));
        Assert.assertEquals(updatedProduct, result);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testProductUpdateThrowsError() {
        Product oldProduct = new Product();
        oldProduct.setProductName("test");
        oldProduct.setPrice(new BigInteger("20"));

        Product newProduct = new Product();
        newProduct.setProductName("test2");
        newProduct.setPrice(new BigInteger("40"));
        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        Product result = managementService.updateProduct(oldProduct.getId(), newProduct);
    }

    @Test
    public void testGetProducts() {
        Product product1 = new Product();
        product1.setProductName("test");
        product1.setPrice(new BigInteger("20"));

        Product product2 = new Product();
        product2.setProductName("test2");
        product2.setPrice(new BigInteger("40"));

        List<Product> products = Lists.newArrayList(product1, product2);

        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<Product> result = managementService.getProducts();
        Assert.assertEquals(products, result);
    }


    @Test
    public void testCreateOrder() {
        String email = "test@test.com";
        List<UUID> productIds = Lists.newArrayList(UUID.randomUUID(), UUID.randomUUID());
        Product product1 = new Product();
        product1.setProductName("Test1");
        product1.setPrice(new BigInteger("20"));

        Product product2 = new Product();
        product2.setProductName("Test2");
        product2.setPrice(new BigInteger("40"));

        List<Product> products = Lists.newArrayList(product1, product2);

        Mockito.when(productRepository.findAllById(any())).thenReturn(products);

        Mockito.when(orderRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Order result = managementService.createOrder(email, productIds);

        Assert.assertEquals(new BigInteger("60"), result.getTotal());
        Assert.assertEquals(email, result.getBuyerEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateOrderGivesError() {
        String email = "test@test.com";
        List<UUID> productIds = Lists.newArrayList(UUID.randomUUID(), UUID.randomUUID());
        Product product1 = new Product();
        product1.setProductName("Test1");
        product1.setPrice(new BigInteger("20"));
        List<Product> products = Lists.newArrayList(product1);

        Mockito.when(productRepository.findAllById(any())).thenReturn(products);

        Order result = managementService.createOrder(email, productIds);
    }


}
