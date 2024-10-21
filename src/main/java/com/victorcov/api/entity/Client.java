package com.victorcov.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document(collection = "clients")
public class Client {
    private String customerId;
    private String name;
    private String email;
    private boolean isActive;
}
