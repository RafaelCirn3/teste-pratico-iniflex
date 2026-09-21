package br.com.iniflex.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelValidationTest {

    private static final LocalDate DATA_VALIDA =
            LocalDate.of(2000, 10, 18);

    @Test
    void deveRejeitarNomeNuloOuVazio() {
        assertThrows(
                NullPointerException.class,
                () -> new Pessoa(null, DATA_VALIDA)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Pessoa("   ", DATA_VALIDA)
        );
    }

    @Test
    void deveNormalizarEspacosDoNome() {
        Pessoa pessoa = new Pessoa("  Maria  ", DATA_VALIDA);

        assertEquals("Maria", pessoa.getNome());
    }

    @Test
    void deveRejeitarDataDeNascimentoNulaOuFutura() {
        assertThrows(
                NullPointerException.class,
                () -> new Pessoa("Maria", null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Pessoa(
                        "Maria",
                        LocalDate.now().plusDays(1)
                )
        );
    }

    @Test
    void deveRejeitarFuncaoNulaOuVazia() {
        assertThrows(
                NullPointerException.class,
                () -> criarFuncionario(BigDecimal.ONE, null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> criarFuncionario(BigDecimal.ONE, "   ")
        );
    }

    @Test
    void deveNormalizarEspacosDaFuncao() {
        Funcionario funcionario = criarFuncionario(
                BigDecimal.ONE,
                "  Operador  "
        );

        assertEquals("Operador", funcionario.getFuncao());
    }

    @Test
    void deveRejeitarSalarioNuloOuNegativoNoCadastro() {
        assertThrows(
                NullPointerException.class,
                () -> criarFuncionario(null, "Operador")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> criarFuncionario(
                        new BigDecimal("-0.01"),
                        "Operador"
                )
        );
    }

    @Test
    void deveRejeitarSalarioNuloOuNegativoNaAtualizacao() {
        Funcionario funcionario = criarFuncionario(
                new BigDecimal("2009.44"),
                "Operador"
        );

        assertThrows(
                NullPointerException.class,
                () -> funcionario.setSalario(null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> funcionario.setSalario(
                        new BigDecimal("-0.01")
                )
        );
        assertEquals(
                new BigDecimal("2009.44"),
                funcionario.getSalario()
        );
    }

    @Test
    void deveAceitarSalarioIgualAZero() {
        assertDoesNotThrow(() ->
                criarFuncionario(BigDecimal.ZERO, "Operador")
        );
    }

    private static Funcionario criarFuncionario(
            BigDecimal salario,
            String funcao
    ) {
        return new Funcionario(
                "Maria",
                DATA_VALIDA,
                salario,
                funcao
        );
    }
}
