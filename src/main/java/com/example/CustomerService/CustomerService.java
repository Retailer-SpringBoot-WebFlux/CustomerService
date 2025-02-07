package com.example.CustomerService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

    @Service
    @RequiredArgsConstructor
    public class CustomerService {

        private final CustomerRepository repository;

        public Mono<Customer> createCustomer(Customer customer) {
            return repository.save(customer)
                    .doOnSuccess(savedCustomer -> System.out.println("Customer saved with ID: " + savedCustomer.getId()));
        }
        public Flux<Customer> getAllCustomers(){
            return repository.findAll()
                    .doOnComplete(() -> System.out.println("Successfully retrieved all products"))
                    .doOnError(error -> System.out.println("Error retrieving products"));
        }
}