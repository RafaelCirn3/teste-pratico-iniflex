package br.com.iniflex;

import br.com.iniflex.model.Funcionario;
import br.com.iniflex.service.FuncionarioService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class Principal {

    static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("0.10");
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Locale LOCALE_BRASIL = Locale.forLanguageTag("pt-BR");
    private static final FuncionarioService FUNCIONARIO_SERVICE =
            new FuncionarioService();

    private Principal() {
    }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = criarFuncionarios();

        FUNCIONARIO_SERVICE.removerPorNome(funcionarios, "João");

        System.out.println("\n--- FUNCIONÁRIOS ---");
        funcionarios.forEach(Principal::imprimirFuncionario);

        FUNCIONARIO_SERVICE.aplicarAumento(
                funcionarios,
                PERCENTUAL_AUMENTO
        );

        Map<String, List<Funcionario>> funcionariosPorFuncao =
                FUNCIONARIO_SERVICE.agruparPorFuncao(funcionarios);

        System.out.println("\n--- FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO ---");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\n" + funcao + ":");
            lista.forEach(Principal::imprimirFuncionario);
        });

        System.out.println("\n--- ANIVERSARIANTES DOS MESES 10 E 12 ---");
        FUNCIONARIO_SERVICE
                .filtrarAniversariantes(funcionarios, Set.of(10, 12))
                .forEach(Principal::imprimirFuncionario);

        FUNCIONARIO_SERVICE.encontrarMaisVelho(funcionarios)
                .ifPresent(funcionario -> {
                    int idade = FUNCIONARIO_SERVICE.calcularIdade(
                            funcionario.getDataNascimento(),
                            LocalDate.now()
                    );

                    System.out.println(
                            "\n--- FUNCIONÁRIO COM MAIOR IDADE ---"
                    );
                    System.out.println("Nome: " + funcionario.getNome());
                    System.out.println("Idade: " + idade);
                });

        System.out.println("\n--- FUNCIONÁRIOS EM ORDEM ALFABÉTICA ---");
        FUNCIONARIO_SERVICE.ordenarPorNome(funcionarios)
                .forEach(Principal::imprimirFuncionario);

        System.out.println(
                "\nTotal dos salários: R$ "
                        + formatarNumero(
                                FUNCIONARIO_SERVICE.somarSalarios(funcionarios)
                        )
        );

        System.out.println("\n--- SALÁRIOS MÍNIMOS POR FUNCIONÁRIO ---");
        funcionarios.forEach(funcionario ->
                System.out.println(
                        funcionario.getNome()
                                + ": "
                                + formatarNumero(
                                        FUNCIONARIO_SERVICE
                                                .calcularSalariosMinimos(
                                                        funcionario.getSalario(),
                                                        SALARIO_MINIMO
                                                )
                                )
                                + " salários mínimos"
                )
        );
    }

    public static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(criarFuncionario(
                "Maria", 2000, 10, 18, "2009.44", "Operador"));
        funcionarios.add(criarFuncionario(
                "João", 1990, 5, 12, "2284.38", "Operador"));
        funcionarios.add(criarFuncionario(
                "Caio", 1961, 5, 2, "9836.14", "Coordenador"));
        funcionarios.add(criarFuncionario(
                "Miguel", 1988, 10, 14, "19119.88", "Diretor"));
        funcionarios.add(criarFuncionario(
                "Alice", 1995, 1, 5, "2234.68", "Recepcionista"));
        funcionarios.add(criarFuncionario(
                "Heitor", 1999, 11, 19, "1582.72", "Operador"));
        funcionarios.add(criarFuncionario(
                "Arthur", 1993, 3, 31, "4071.84", "Contador"));
        funcionarios.add(criarFuncionario(
                "Laura", 1994, 7, 8, "3017.45", "Gerente"));
        funcionarios.add(criarFuncionario(
                "Heloisa", 2003, 5, 24, "1606.85", "Eletricista"));
        funcionarios.add(criarFuncionario(
                "Helena", 1996, 9, 2, "2799.93", "Gerente"));

        return funcionarios;
    }

    private static Funcionario criarFuncionario(
            String nome,
            int ano,
            int mes,
            int dia,
            String salario,
            String funcao
    ) {
        return new Funcionario(
                nome,
                LocalDate.of(ano, mes, dia),
                new BigDecimal(salario),
                funcao
        );
    }

    private static void imprimirFuncionario(Funcionario funcionario) {
        System.out.println(
                "Nome: " + funcionario.getNome()
                        + " | Nascimento: "
                        + funcionario.getDataNascimento().format(FORMATO_DATA)
                        + " | Salário: "
                        + formatarNumero(funcionario.getSalario())
                        + " | Função: "
                        + funcionario.getFuncao()
        );
    }

    private static String formatarNumero(BigDecimal valor) {
        NumberFormat formato = NumberFormat.getNumberInstance(LOCALE_BRASIL);
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        return formato.format(valor);
    }
}
