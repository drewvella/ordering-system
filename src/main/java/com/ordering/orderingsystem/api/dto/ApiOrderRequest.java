package com.ordering.orderingsystem.api.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ApiOrderRequest {

    private String buyerEmail;
    private List<UUID> products;
}
