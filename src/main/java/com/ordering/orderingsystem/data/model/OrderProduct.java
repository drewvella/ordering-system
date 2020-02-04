package com.ordering.orderingsystem.data.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.UUID;

@Data
@Entity
public class OrderProduct {
    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    private String productName;
    private BigInteger productPrice;
}
