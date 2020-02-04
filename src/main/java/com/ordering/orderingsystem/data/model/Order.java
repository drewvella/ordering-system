package com.ordering.orderingsystem.data.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Order {

    @Id
    private UUID id = UUID.randomUUID();
    private String buyerEmail;
    private OffsetDateTime createdDateTime = OffsetDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> products;

    private BigInteger total;

    public void addOrderProducts(List<OrderProduct> orderProducts) {
        orderProducts.forEach(orderProduct -> orderProduct.setOrder(this));
        setProducts(orderProducts);
    }
}
