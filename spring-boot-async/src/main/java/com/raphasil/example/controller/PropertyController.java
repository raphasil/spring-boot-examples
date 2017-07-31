package com.raphasil.example.controller;

import com.raphasil.example.property.ApplicationProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by raphael on 29/07/17.
 */
@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final ApplicationProperty properties;

    @Autowired
    public PropertyController(ApplicationProperty properties) {
        this.properties = properties;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getProperties() {
        return ResponseEntity.ok(properties);
    }

}
