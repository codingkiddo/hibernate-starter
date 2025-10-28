package com.codingkiddo.hibernateapp.repository;

import com.codingkiddo.hibernateapp.domain.Customer;
import com.codingkiddo.hibernateapp.web.dto.CustomerSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @EntityGraph(attributePaths = {"orders"})
    Optional<Customer> findWithOrdersById(Long id);

    Page<Customer> findAllByStatus(Customer.Status status, Pageable pageable);

    Optional<Customer> findByEmail(String email);

    @Query("""
select new com.codingkiddo.hibernateapp.web.dto.CustomerSummary(
  c.id, c.email, c.name, count(o.id)
) from Customer c left join c.orders o
where c.status = :status
group by c.id, c.email, c.name
""")
    Page<CustomerSummary> findSummariesByStatus(@Param("status") Customer.Status status, Pageable pageable);
}
