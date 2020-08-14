package com.raphasil.example.async.controller;

import com.raphasil.example.async.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * Created by raphael on 29/07/17.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(path = "/sync", method = RequestMethod.GET)
  public ResponseEntity<?> getUserSync() {
    LOG.debug("BEGIN");
    final String result = userService.getOneUserSync();
    LOG.debug("END ");
    return ResponseEntity.ok(result);
  }

  // CompletableFuture is a thread managed by Spring Application
  @RequestMapping(path = "/callable", method = RequestMethod.GET)
  public Callable<ResponseEntity<?>> getUserCallable() {
    LOG.debug("BEGIN");
    LOG.debug("END ");
    return () -> {
      LOG.debug("Callable - BEGIN");
      final String result = userService.getOneUserSync();
      LOG.debug("Callable - END");
      return ResponseEntity.ok(result);
    };
  }

  // CompletableFuture is a thread managed by us
  @RequestMapping(path = "/deferred", method = RequestMethod.GET)
  public DeferredResult<ResponseEntity<?>> getUserDeferred() {
    LOG.debug("BEGIN");
    final DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();

    final CompletableFuture<String> userAsync = userService.getOneUserAsync();
    userAsync.whenComplete((result, ex) -> {
      LOG.debug("getUserDeferred - CompletableFuture - BEGIN");
      if (ex != null) {
        deferredResult.setErrorResult(ex);
      } else {
        deferredResult.setResult(ResponseEntity.ok(result));
      }
      LOG.debug("getUserDeferred - CompletableFuture - END");
    });

    LOG.debug("END ");
    return deferredResult;
  }

}
