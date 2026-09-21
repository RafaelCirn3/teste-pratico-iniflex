package br.com.iniflex.service;

import br.com.iniflex.model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FuncionarioService {

    public void removerPorNome(
            List<Funcionario> funcionarios,
            String nome
    ) {
        funcionarios.removeIf(funcionario ->
                funcionario.getNome().equals(nome));
    }

    public void aplicarAumento(
            List<Funcionario> funcionarios,
            BigDecimal percentual
    ) {
        BigDecimal fator = BigDecimal.ONE.add(percentual);

        funcionarios.forEach(funcionario ->
                funcionario.setSalario(
                        funcionario.getSalario()
                                .multiply(fator)
                                .setScale(2, RoundingMode.HALF_UP)
                )
        );
    }

    public Map<String, List<Funcionario>> agruparPorFuncao(
            List<Funcionario> funcionarios
    ) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    public List<Funcionario> filtrarAniversariantes(
            List<Funcionario> funcionarios,
            Set<Integer> meses
    ) {
        return funcionarios.stream()
                .filter(funcionario -> meses.contains(
                        funcionario.getDataNascimento().getMonthValue()
                ))
                .toList();
    }

    public Optional<Funcionario> encontrarMaisVelho(
            List<Funcionario> funcionarios
    ) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento));
    }

    public int calcularIdade(
            LocalDate dataNascimento,
            LocalDate dataReferencia
    ) {
        return Period.between(dataNascimento, dataReferencia).getYears();
    }

    public List<Funcionario> ordenarPorNome(
            List<Funcionario> funcionarios
    ) {
        return funcionarios.stream()
                .sorted(Comparator.comparing(
                        Funcionario::getNome,
                        String.CASE_INSENSITIVE_ORDER
                ))
                .toList();
    }

    public BigDecimal somarSalarios(
            List<Funcionario> funcionarios
    ) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularSalariosMinimos(
            BigDecimal salario,
            BigDecimal salarioMinimo
    ) {
        return salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }
}
