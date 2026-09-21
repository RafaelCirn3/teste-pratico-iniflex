package br.com.iniflex.model;

import java.time.LocalDate;
import java.util.Objects;

public class Pessoa {

    private final String nome;
    private final LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = validarTexto(nome, "O nome");
        this.dataNascimento = validarDataNascimento(dataNascimento);
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    private static String validarTexto(String valor, String campo) {
        Objects.requireNonNull(valor, campo + " não pode ser nulo");

        String valorNormalizado = valor.trim();
        if (valorNormalizado.isEmpty()) {
            throw new IllegalArgumentException(
                    campo + " não pode ser vazio"
            );
        }

        return valorNormalizado;
    }

    private static LocalDate validarDataNascimento(
            LocalDate dataNascimento
    ) {
        Objects.requireNonNull(
                dataNascimento,
                "A data de nascimento não pode ser nula"
        );

        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data de nascimento não pode estar no futuro"
            );
        }

        return dataNascimento;
    }
}
