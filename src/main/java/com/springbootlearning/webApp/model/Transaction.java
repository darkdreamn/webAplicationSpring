package com.springbootlearning.webApp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Getter
    @NotNull(message = "valor é obrigatório.")
    private double valor;

    @Getter
    @NotBlank(message = "data e hora são obrigatórias.")
    private String dataHora;
}
