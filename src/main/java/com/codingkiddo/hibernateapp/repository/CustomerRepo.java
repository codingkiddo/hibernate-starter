package com.codingkiddo.hibernateapp.repository;

import com.codingkiddo.hibernateapp.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @EntityGraph(attributePaths = {"orders"})
    Optional<Customer> findWithOrdersById(Long id);

    Page<Customer> findAllByStatus(Customer.Status status, Pageable pageable);

    Optional<Customer> findByEmail(String email);
}
