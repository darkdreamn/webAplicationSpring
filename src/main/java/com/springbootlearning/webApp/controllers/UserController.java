package com.springbootlearning.webApp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootlearning.webApp.model.Transaction;

@RestController
public class UserController {
    private List<Transaction> transactions = new ArrayList<>();

    @PostMapping("/transacao")
    public ResponseEntity<String> transaction(@RequestBody Transaction transaction) {
        transactions.add(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body("A transação foi aceita");
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<String> deleteTransaction() {
        transactions.clear();
        return ResponseEntity.status(HttpStatus.OK).body("Todas as informações foram apagadas com sucesso");
    }

    @GetMapping("/estatistica")
    public List<Transaction> listTransactions() {
        return transactions;
    }
}
