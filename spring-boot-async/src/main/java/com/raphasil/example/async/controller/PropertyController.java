package com.raphasil.example.async.controller;

import com.raphasil.example.async.property.ApplicationProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger LOG = LoggerFactory.getLogger(PropertyController.class);

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
