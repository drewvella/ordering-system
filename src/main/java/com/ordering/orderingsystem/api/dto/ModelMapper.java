package com.ordering.orderingsystem.api.dto;

import com.ordering.orderingsystem.data.model.Order;
import com.ordering.orderingsystem.data.model.OrderProduct;
import com.ordering.orderingsystem.data.model.Product;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ModelMapper extends ConfigurableMapper {

    @Override
    public void configure(MapperFactory factory) {
        super.configure(factory);


        factory.registerClassMap(factory.classMap(ApiProduct.class, Product.class).exclude("id").byDefault()
                .customize(new CustomMapper<ApiProduct, Product>() {
                    @Override
                    public void mapBtoA(Product product, ApiProduct apiProduct, MappingContext context) {
                        apiProduct.setId(product.getId());
                    }
                }).toClassMap());

        factory.registerClassMap(factory.classMap(Product.class, OrderProduct.class).exclude("id").byDefault()
                .customize(new CustomMapper<Product, OrderProduct>() {
                    @Override
                    public void mapAtoB(Product product, OrderProduct orderProduct, MappingContext context) {
                        orderProduct.setProductName(product.getProductName());
                        orderProduct.setProductPrice(product.getPrice());
                        orderProduct.setProduct(product);
                    }
                }).toClassMap());

        factory.registerClassMap(factory.classMap(OrderProduct.class, ApiProduct.class).byDefault()
                .customize(new CustomMapper<OrderProduct, ApiProduct>() {
                    @Override
                    public void mapAtoB(OrderProduct orderProduct, ApiProduct apiProduct, MappingContext context) {
                        apiProduct.setProductName(orderProduct.getProductName());
                        apiProduct.setPrice(orderProduct.getProductPrice());
                        apiProduct.setId(orderProduct.getId());
                    }
                }).toClassMap());

    }
}
