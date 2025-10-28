package com.codingkiddo.hibernateapp.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateCustomerRequest {
    @NotBlank @Email
    public String email;
    @NotBlank
    public String name;
}
