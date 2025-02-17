package com.example.CustomerService;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
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
        @KafkaListener(topics = "order-topic", groupId = "customer-group")
        public void consumeOrderEvent(OrderEvent event) {
            System.out.println("Validating customer for Order ID: " + event.getProductId());

            repository.findById(event.getCustomerId())
                    .doOnSuccess(customer -> {
                        if (customer != null) {
                            System.out.println("Customer exists: " + customer.getName());
                        } else {
                            System.err.println("Customer ID not found: " + event.getCustomerId());
                        }
                    })
                    .doOnError(error -> System.err.println("Failed to validate customer: " + error.getMessage()))
                    .subscribe();
        }

    }