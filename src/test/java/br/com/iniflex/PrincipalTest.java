package br.com.iniflex;

import br.com.iniflex.model.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrincipalTest {

    @Test
    void deveCriarFuncionariosNaOrdemDoEnunciado() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();

        assertEquals(10, funcionarios.size());
        assertIterableEquals(
                List.of(
                        "Maria", "João", "Caio", "Miguel", "Alice",
                        "Heitor", "Arthur", "Laura", "Heloisa", "Helena"
                ),
                nomes(funcionarios)
        );
    }

    @Test
    void deveRemoverFuncionarioPeloNome() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();

        Principal.removerFuncionarioPorNome(funcionarios, "João");

        assertEquals(9, funcionarios.size());
        assertFalse(nomes(funcionarios).contains("João"));
    }

    @Test
    void deveAplicarAumentoDeDezPorCentoComDuasCasasDecimais() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();
        Principal.removerFuncionarioPorNome(funcionarios, "João");

        Principal.aplicarAumento(funcionarios, new BigDecimal("0.10"));

        assertEquals(new BigDecimal("2210.38"), funcionarios.get(0).getSalario());
        assertEquals(
                new BigDecimal("50906.82"),
                Principal.somarSalarios(funcionarios)
        );
    }

    @Test
    void deveAgruparFuncionariosPorFuncao() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();
        Principal.removerFuncionarioPorNome(funcionarios, "João");

        Map<String, List<Funcionario>> grupos =
                Principal.agruparPorFuncao(funcionarios);

        assertEquals(7, grupos.size());
        assertIterableEquals(
                List.of("Maria", "Heitor"),
                nomes(grupos.get("Operador"))
        );
        assertIterableEquals(
                List.of("Laura", "Helena"),
                nomes(grupos.get("Gerente"))
        );
    }

    @Test
    void deveFiltrarAniversariantesDeOutubroEDezembro() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();

        List<Funcionario> aniversariantes =
                Principal.filtrarAniversariantes(
                        funcionarios,
                        Set.of(10, 12)
                );

        assertIterableEquals(
                List.of("Maria", "Miguel"),
                nomes(aniversariantes)
        );
    }

    @Test
    void deveEncontrarFuncionarioMaisVelhoECalcularIdade() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();
        Principal.removerFuncionarioPorNome(funcionarios, "João");

        Funcionario maisVelho = Principal.funcionarioMaisVelho(funcionarios)
                .orElseThrow();

        assertEquals("Caio", maisVelho.getNome());
        assertEquals(
                65,
                Principal.calcularIdade(
                        maisVelho.getDataNascimento(),
                        LocalDate.of(2026, 9, 21)
                )
        );
    }

    @Test
    void deveOrdenarFuncionariosPorNomeSemAlterarListaOriginal() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();
        Principal.removerFuncionarioPorNome(funcionarios, "João");

        List<Funcionario> ordenados = Principal.ordenarPorNome(funcionarios);

        assertIterableEquals(
                List.of(
                        "Alice", "Arthur", "Caio", "Heitor", "Helena",
                        "Heloisa", "Laura", "Maria", "Miguel"
                ),
                nomes(ordenados)
        );
        assertEquals("Maria", funcionarios.get(0).getNome());
    }

    @Test
    void deveSomarSalarios() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();
        Principal.removerFuncionarioPorNome(funcionarios, "João");

        assertEquals(
                new BigDecimal("46278.93"),
                Principal.somarSalarios(funcionarios)
        );
    }

    @Test
    void deveCalcularQuantidadeDeSalariosMinimos() {
        BigDecimal quantidade = Principal.calcularSalariosMinimos(
                new BigDecimal("2009.44"),
                Principal.SALARIO_MINIMO
        );

        assertEquals(new BigDecimal("1.66"), quantidade);
    }

    @Test
    void modelsDevemRejeitarAtributosNulos() {
        assertThrows(
                NullPointerException.class,
                () -> new Funcionario(
                        null,
                        LocalDate.of(2000, 1, 1),
                        BigDecimal.ONE,
                        "Operador"
                )
        );

        Funcionario funcionario = new Funcionario(
                "Maria",
                LocalDate.of(2000, 10, 18),
                BigDecimal.ONE,
                "Operador"
        );

        assertThrows(
                NullPointerException.class,
                () -> funcionario.setSalario(null)
        );
        assertTrue(funcionario.getSalario().signum() > 0);
    }

    private static List<String> nomes(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getNome)
                .toList();
    }
}
