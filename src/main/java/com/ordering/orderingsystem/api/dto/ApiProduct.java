package com.ordering.orderingsystem.api.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class ApiProduct {
    private UUID id;
    private String productName;
    private BigInteger price;
}
