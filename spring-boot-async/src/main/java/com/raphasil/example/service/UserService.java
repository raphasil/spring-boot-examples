package com.raphasil.example.service;

import com.raphasil.example.property.ApplicationProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Created by raphael on 29/07/17.
 */
@Service
public class UserService {

    private final ApplicationProperty properties;
    private final RestTemplate restTemplate;
    private final AsyncRestTemplate asyncRestTemplate;

    @Autowired
    public UserService(ApplicationProperty properties, RestTemplate restTemplate, AsyncRestTemplate asyncRestTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
        this.asyncRestTemplate = asyncRestTemplate;
    }

    @Async
    public Future<String> getOneUser() {
        System.out.println("UserService - getOneUser - BEGIN");

        AsyncResult<String> result;

        try {
            final ListenableFuture<ResponseEntity<String>> request = asyncRestTemplate.getForEntity(properties.getClients().getRandomUser().getUrl(), String.class);

            final ResponseEntity<String> response = request.get();

            if(response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                result = new AsyncResult<>(response.getBody());
            } else {
                result = new AsyncResult<>("Result: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            result = new AsyncResult<>(e.getMessage());
        }

        System.out.println("UserService - getOneUser - END");

        return result;
    }
}
