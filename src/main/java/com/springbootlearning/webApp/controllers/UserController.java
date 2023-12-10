package com.springbootlearning.webApp.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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
    private List<Transaction> transactions = new ArrayList<Transaction>();

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
        LocalDateTime agora = LocalDateTime.now();
        // TODO: atualizar de hora para minuto depois
        LocalDateTime limitePassado = agora.minusHours(1);

        DateTimeFormatter formatarStringParaDate = DateTimeFormatter.ISO_DATE_TIME;

        if (transactions.isEmpty()) {
            System.out.println("Não há transações");
            return transactions;
        } else {
            transactions.sort(Comparator.comparing(Transaction::getDataHora).reversed());
            int i = 0;
            LocalDateTime stringEmDateTime;

            do {
                stringEmDateTime = LocalDateTime.parse(transactions.get(i).getDataHora(), formatarStringParaDate);
                if (stringEmDateTime.isAfter(limitePassado)) {
                    System.out.println("stringEmDateTime: " + stringEmDateTime);
                    System.out.println("limitepassado: " + limitePassado);
                    i++;
                } else {
                    System.out.println("Fora do tempo");
                    break;
                }

            } while (transactions.size() > i);

        }

        return transactions;
    }
}
