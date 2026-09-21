package br.com.iniflex.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Funcionario extends Pessoa {

    private BigDecimal salario;
    private final String funcao;

    public Funcionario(
            String nome,
            LocalDate dataNascimento,
            BigDecimal salario,
            String funcao
    ) {
        super(nome, dataNascimento);
        this.salario = Objects.requireNonNull(salario, "O salário não pode ser nulo");
        this.funcao = Objects.requireNonNull(funcao, "A função não pode ser nula");
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = Objects.requireNonNull(salario, "O salário não pode ser nulo");
    }

    public String getFuncao() {
        return funcao;
    }
}
