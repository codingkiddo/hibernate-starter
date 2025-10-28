package com.codingkiddo.hibernateapp.repository;

import com.codingkiddo.hibernateapp.domain.Order;
import com.codingkiddo.hibernateapp.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);
}
