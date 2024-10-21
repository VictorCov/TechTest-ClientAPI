package com.victorcov.api.controller;

import com.victorcov.api.entity.Client;
import com.victorcov.api.repository.ClientRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    // 1. Get all clients
    @Operation(summary = "Get all clients")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // 2. Get a client by ID
    @Operation(summary = "Get a client by customerId")
    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Client> getClientsBycustomerId(@PathVariable String customerId) {
        System.out.println("Inside get client by client id");
        return clientRepository.findByCustomerId(customerId).next().switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with ID: " + customerId)));

    }

    // 3. Get clients by multiple IDs
    @Operation(summary = "Get clients by multiple client IDs")
    @GetMapping(value = "/by-ids", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Client> getClientByIds(@RequestParam String ids) {
        List<String> customerIds = Arrays.asList(ids.split(","));
        return clientRepository.findByCustomerIdIn(customerIds);
    }
}