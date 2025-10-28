package com.codingkiddo.hibernateapp.web;

import com.codingkiddo.hibernateapp.domain.Customer;
import com.codingkiddo.hibernateapp.repository.CustomerRepo;
import com.codingkiddo.hibernateapp.service.CustomerService;
import com.codingkiddo.hibernateapp.web.dto.CreateCustomerRequest;
import com.codingkiddo.hibernateapp.web.dto.CreateOrderRequest;
import com.codingkiddo.hibernateapp.web.dto.CustomerDto;
import com.codingkiddo.hibernateapp.web.dto.CustomerSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService service;
    private final CustomerRepo customerRepo;

    public CustomerController(CustomerService service, CustomerRepo customerRepo) {
        this.service = service;
        this.customerRepo = customerRepo;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> createCustomer(@Validated @RequestBody CreateCustomerRequest req) {
        var c = service.createCustomer(req.email, req.name);
        return ResponseEntity.created(URI.create("/api/customers/" + c.getId()))
                .body(toDto(c, false));
    }

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable Long id,
                                   @RequestParam(name = "withOrders", defaultValue = "true") boolean withOrders) {
        var c = withOrders ? service.getWithOrders(id) :
                customerRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
        return toDto(c, withOrders);
    }

    @GetMapping("/customers")
    public Page<CustomerDto> listCustomers(
            @RequestParam(defaultValue = "ACTIVE") Customer.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepo.findAllByStatus(status, pageable)
                .map(c -> toDto(c, false));
    }

    @GetMapping("/customers/summaries")
    public Page<CustomerSummary> listCustomerSummaries(
            @RequestParam(defaultValue = "ACTIVE") Customer.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepo.findSummariesByStatus(status, pageable);
    }

    @PostMapping("/customers/{id}/orders")
    public ResponseEntity<CustomerDto.OrderDto> placeOrder(@PathVariable Long id,
                                                           @Validated @RequestBody CreateOrderRequest req) {
        var o = service.placeOrder(id, req.amount);
        return ResponseEntity.created(URI.create("/api/customers/" + id + "/orders/" + o.getId()))
                .body(new CustomerDto.OrderDto(o.getId(), o.getAmount(), o.getCreatedAt()));
    }

    @GetMapping("/customers/{id}/orders")
    public java.util.List<CustomerDto.OrderDto> listOrders(@PathVariable Long id) {
        return service.listOrders(id).stream()
                .map(o -> new CustomerDto.OrderDto(o.getId(), o.getAmount(), o.getCreatedAt()))
                .collect(Collectors.toList());
    }

    private static CustomerDto toDto(Customer c, boolean includeOrders) {
        var orders = includeOrders ? c.getOrders().stream()
                .map(o -> new CustomerDto.OrderDto(o.getId(), o.getAmount(), o.getCreatedAt()))
                .collect(Collectors.toList()) : java.util.List.<CustomerDto.OrderDto>of();
        return new CustomerDto(c.getId(), c.getEmail(), c.getName(), c.getStatus().name(),
                c.getCreatedAt(), c.getUpdatedAt(), orders);
    }
}
