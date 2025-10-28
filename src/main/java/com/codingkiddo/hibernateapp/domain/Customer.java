package com.codingkiddo.hibernateapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers", indexes = {
    @Index(name="ux_customer_email", columnList = "email", unique = true),
    @Index(name="ix_customer_status", columnList = "status")
})
@NaturalIdCache
public class Customer {

    public enum Status { ACTIVE, SUSPENDED }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 50)
    private Long id;

    @NotBlank @Email
    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Status status = Status.ACTIVE;

    @Version
    private long version;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    private Set<Order> orders = new HashSet<>();

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public long getVersion() { return version; }
    public void setVersion(long version) { this.version = version; }

    public Set<Order> getOrders() { return orders; }
    public void setOrders(Set<Order> orders) { this.orders = orders; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}
