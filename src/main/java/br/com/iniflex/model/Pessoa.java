package br.com.iniflex.model;

import java.time.LocalDate;
import java.util.Objects;

public class Pessoa {

    private final String nome;
    private final LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = Objects.requireNonNull(nome, "O nome não pode ser nulo");
        this.dataNascimento = Objects.requireNonNull(
                dataNascimento,
                "A data de nascimento não pode ser nula"
        );
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
