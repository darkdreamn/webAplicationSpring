package com.springbootlearning.webApp.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
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
    public ResponseEntity<JSONObject> listTransactions() {
        LocalDateTime agora = LocalDateTime.now();
        // TODO: atualizar de hora para minuto depois
        LocalDateTime limitePassado = agora.minusHours(1);
        DateTimeFormatter formatarStringParaDate = DateTimeFormatter.ISO_DATE_TIME;

        HashMap<String, Object> result = new HashMap<String, Object>();

        if (transactions.isEmpty()) {
            System.out.println("Não há transações");
            result.put("A quantidade das transações feitas", 0);
            result.put("O valor total das transações", 0);
            result.put("A média das transações", 0);
            result.put("A menor transação", 0);
            result.put("A maior transação", 0);
        } else {
            transactions.sort(Comparator.comparing(Transaction::getDataHora).reversed());
            int quantidadeTransacoes = 0;
            int i = 0;
            double menorTransacao = Integer.MAX_VALUE;
            double maiorTransacao = Integer.MIN_VALUE;
            double valorTotal = 0;
            LocalDateTime stringEmDateTime;
            double media;

            do {
                stringEmDateTime = LocalDateTime.parse(transactions.get(i).getDataHora(), formatarStringParaDate);
                if (stringEmDateTime.isAfter(limitePassado)) {
                    double valorAtual = transactions.get(i).getValor();

                    if (menorTransacao > valorAtual)
                        menorTransacao = valorAtual;
                    if (maiorTransacao < valorAtual)
                        maiorTransacao = valorAtual;

                    valorTotal += valorAtual;
                    quantidadeTransacoes++;
                    i++;
                } else {
                    System.out.println("Fora do tempo");
                    break;
                }
            } while (transactions.size() > i);

            media = valorTotal / quantidadeTransacoes;

            result.put("A quantidade das transações feitas", quantidadeTransacoes);
            result.put("O valor total das transações", valorTotal);
            result.put("A média das transações", media);
            result.put("A menor transação", menorTransacao);
            result.put("A maior transação", maiorTransacao);
        }
        JSONObject resultJson = new JSONObject(result);

        return ResponseEntity.status(HttpStatus.OK).body(resultJson);
    }
}
