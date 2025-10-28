package com.codingkiddo.hibernateapp.web.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateOrderRequest {
    @NotNull
    public BigDecimal amount;
}
