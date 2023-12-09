package com.springbootlearning.webApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootlearning.webApp.model.Transaction;

@RestController
public class UserController {
    @PostMapping("/transacao")
    public ResponseEntity<String> transaction(@RequestBody Transaction transaction) {

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
