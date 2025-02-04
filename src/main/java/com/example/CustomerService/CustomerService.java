package com.example.CustomerService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

    @Service
    @RequiredArgsConstructor
    public class CustomerService {

        private final CustomerRepository repository;

        public Mono<Customer> createCustomer(Customer customer) {
            return repository.save(customer)
                    .doOnSuccess(savedCustomer -> System.out.println("Customer saved with ID: " + savedCustomer.getId()));
        }
}