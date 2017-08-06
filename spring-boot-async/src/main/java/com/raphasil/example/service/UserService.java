package com.raphasil.example.service;

import com.raphasil.example.property.ApplicationProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by raphael on 29/07/17.
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final ApplicationProperty properties;
    private final AsyncRestTemplate asyncRestTemplate;

    @Autowired
    public UserService(ApplicationProperty properties, AsyncRestTemplate asyncRestTemplate) {
        this.properties = properties;
        this.asyncRestTemplate = asyncRestTemplate;
    }

    @Async
    public CompletableFuture<String> getOneUserAsync() {
        LOG.debug("BEGIN");

        final String result = getOneUserSync();

        LOG.debug("END");

        return CompletableFuture.completedFuture(result);
    }

    public String getOneUserSync() {
        LOG.debug("BEGIN");

        String result;

        try {
            final ListenableFuture<ResponseEntity<String>> request = asyncRestTemplate.getForEntity(properties.getClients().getRandomUser().getUrl(), String.class);

            final ResponseEntity<String> response = request.get();

            if(response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                result = response.getBody();
            } else {
                result = "Result: " + response.getStatusCodeValue();
            }
        } catch (Exception e) {
            result = e.getMessage();
        }

        LOG.debug("END");

        return result;
    }
}
