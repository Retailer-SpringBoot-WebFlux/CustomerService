package com.example.CustomerService;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, Long> {
    Mono<Customer> findByName(String name); // Example of a reactive method
    Mono<Customer> findByEmail(String email);

}