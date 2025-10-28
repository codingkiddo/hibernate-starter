package com.codingkiddo.hibernateapp.web.dto;

public record CustomerSummary(Long id, String email, String name, long ordersCount) {}
