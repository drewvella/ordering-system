package com.ordering.orderingsystem.data.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Product {


    @Id
    private UUID id = UUID.randomUUID();
    private String productName;
    private BigInteger price;
    private OffsetDateTime createdDateTime = OffsetDateTime.now();
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;
}
