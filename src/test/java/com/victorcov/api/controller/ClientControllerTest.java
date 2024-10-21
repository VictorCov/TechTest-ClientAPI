package com.victorcov.api.controller;

import com.victorcov.api.entity.Client;
import com.victorcov.api.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ClientControllerTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClients() {
        Client client1 = new Client("1", "Client1", "client@mail.com", true );
        Client client2 = new Client("2", "Client2", "client@mail.com", true);
        when(clientRepository.findAll()).thenReturn(Flux.just(client1, client2));

        StepVerifier.create(clientController.getAllClients())
                .expectNext(client1)
                .expectNext(client2)
                .verifyComplete();
    }

    @Test
    void testGetClientByCustomerId_Found() {
        String customerId = "1";
        Client client = new Client(customerId, "Client1", "client@mail.com", true);
        when(clientRepository.findByCustomerId(eq(customerId))).thenReturn(Flux.just(client));

        StepVerifier.create(clientController.getClientsBycustomerId(customerId))
                .expectNext(client)
                .verifyComplete();
    }

    @Test
    void testGetClientByCustomerId_NotFound() {
        String customerId = "1";
        when(clientRepository.findByCustomerId(eq(customerId))).thenReturn(Flux.empty());

        StepVerifier.create(clientController.getClientsBycustomerId(customerId))
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();
    }

    @Test
    void testGetClientByIds_Found() {
        List<String> customerIds = Arrays.asList("1", "2");
        Client client1 = new Client("1", "Client1", "client@mail.com", true);
        Client client2 = new Client("2", "Client2", "client@mail.com", true);
        when(clientRepository.findByCustomerIdIn(eq(customerIds))).thenReturn(Flux.just(client1, client2));

        StepVerifier.create(clientController.getClientByIds("1,2"))
                .expectNext(client1)
                .expectNext(client2)
                .verifyComplete();
    }

    @Test
    void testGetClientByIds_NotFound() {
        List<String> customerIds = Arrays.asList("1", "2");
        when(clientRepository.findByCustomerIdIn(eq(customerIds))).thenReturn(Flux.empty());

        StepVerifier.create(clientController.getClientByIds("1,2"))
                .verifyComplete();
    }
}