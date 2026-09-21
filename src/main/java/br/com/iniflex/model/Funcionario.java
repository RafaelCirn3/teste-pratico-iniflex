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
        this.salario = validarSalario(salario);
        this.funcao = validarFuncao(funcao);
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = validarSalario(salario);
    }

    public String getFuncao() {
        return funcao;
    }

    private static BigDecimal validarSalario(BigDecimal salario) {
        Objects.requireNonNull(salario, "O salário não pode ser nulo");

        if (salario.signum() < 0) {
            throw new IllegalArgumentException(
                    "O salário não pode ser negativo"
            );
        }

        return salario;
    }

    private static String validarFuncao(String funcao) {
        Objects.requireNonNull(funcao, "A função não pode ser nula");

        String funcaoNormalizada = funcao.trim();
        if (funcaoNormalizada.isEmpty()) {
            throw new IllegalArgumentException(
                    "A função não pode ser vazia"
            );
        }

        return funcaoNormalizada;
    }
}
