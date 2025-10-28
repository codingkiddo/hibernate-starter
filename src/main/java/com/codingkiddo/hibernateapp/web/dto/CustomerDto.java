package com.codingkiddo.hibernateapp.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record CustomerDto(
        Long id,
        String email,
        String name,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<OrderDto> orders
) {
    public record OrderDto(Long id, BigDecimal amount, OffsetDateTime createdAt) {}
}
