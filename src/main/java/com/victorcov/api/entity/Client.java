package com.victorcov.api.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clients")
public class Client {
    private String customerId;
    private String name;
    private String email;
    private boolean isActive;
}
