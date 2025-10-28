package com.codingkiddo.hibernateapp.service;

import com.codingkiddo.hibernateapp.domain.Customer;
import com.codingkiddo.hibernateapp.domain.Order;
import com.codingkiddo.hibernateapp.repository.CustomerRepo;
import com.codingkiddo.hibernateapp.repository.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customers;
    private final OrderRepo orders;

    public CustomerService(CustomerRepo customers, OrderRepo orders) {
        this.customers = customers;
        this.orders = orders;
    }

    @Transactional
    public Customer createCustomer(String email, String name) {
        var c = new Customer();
        c.setEmail(email);
        c.setName(name);
        c.setStatus(Customer.Status.ACTIVE);
        return customers.save(c);
    }

    @Transactional(readOnly = true)
    public Customer getWithOrders(Long id) {
        return customers.findWithOrdersById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
    }

    @Transactional
    public Order placeOrder(Long customerId, BigDecimal amount) {
        var cust = customers.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
        var o = new Order();
        o.setCustomer(cust);
        o.setAmount(amount);
        return orders.save(o);
    }

    @Transactional(readOnly = true)
    public List<Order> listOrders(Long customerId) {
        var cust = customers.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
        return orders.findByCustomerOrderByCreatedAtDesc(cust);
    }
}
