package com.example.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<Customer>> createCustomer(@Validated @RequestBody Customer customer) {
        log.info("Received customer request: {}", customer);
        return service.createCustomer(customer)
                .map(savedCustomer -> {
                    log.info("Customer saved: {}", savedCustomer);
                    return ResponseEntity.ok(savedCustomer);
                })
                .doOnError(error -> log.error("Error saving customer", error));
    }
    @GetMapping
    public Flux<Customer> getAllCustomers(){
        return service.getAllCustomers()
                .doOnComplete(() -> System.out.println("Successfully retrieved all products"))
                .doOnError(error -> System.out.println("Error retrieving products"));

    }
}
