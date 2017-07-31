package com.raphasil.example.controller;

import com.raphasil.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by raphael on 29/07/17.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<ResponseEntity<?>> getUser() {
        System.out.println("UserController - getUser - BEGIN");
        final Future<String> result = userService.getOneUser();
        System.out.println("UserController - getUser - END ");
        return () -> {
            System.out.println("Callable - BEGIN");
            final String res = result.get();
            System.out.println("Callable - END");
            return ResponseEntity.ok(res);
        };
    }

}
