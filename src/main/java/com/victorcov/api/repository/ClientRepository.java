package com.victorcov.api.repository;


import com.victorcov.api.entity.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;


@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    Flux<Client> findByCustomerIdIn(List<String> customerIds);
    Flux<Client> findByCustomerId(String customerId);

}