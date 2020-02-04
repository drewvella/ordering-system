package com.ordering.orderingsystem.api.dto;

import lombok.Data;

import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ApiOrder {
    private UUID id;
    private String buyerEmail;
    private OffsetDateTime createdDateTime;
    private BigInteger total;
}
