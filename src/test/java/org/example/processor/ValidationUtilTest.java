package org.example.processor;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Nested
    @DisplayName("Testes para validarParametrosRegra")
    class ValidarParametrosRegraTest {

        @Test
        @DisplayName("Deve aceitar parâmetros válidos para Regra 1")
        void deveAceitarParametrosValidosParaRegra1() {
            // Arrange
            String[] params = {"0", "5", "10", "15"};

            // Act & Assert
            assertDoesNotThrow(() -> ValidationUtil.validarParametrosRegra(params, 1, 4, true));
        }

        @Test
        @DisplayName("Deve aceitar parâmetros válidos para Regra 2")
        void deveAceitarParametrosValidosParaRegra2() {
            // Arrange
            String[] params = {"1", "5", "10"};

            // Act & Assert
            assertDoesNotThrow(() -> ValidationUtil.validarParametrosRegra(params, 2, 3, false));
        }

        @Test
        @DisplayName("Deve rejeitar quando número de parâmetros está incorreto para Regra 1")
        void deveRejeitarNumeroIncorretoDeParametrosRegra1() {
            // Arrange
            String[] params = {"0", "5", "10"}; // Falta o parâmetro i

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarParametrosRegra(params, 1, 4, true));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Formato inválido para Regra 1"));
            assertTrue(exception.getMessage().contains("'0 t q i'"));
            assertTrue(exception.getMessage().contains("4 parâmetros"));
        }

        @Test
        @DisplayName("Deve rejeitar quando número de parâmetros está incorreto para Regra 2")
        void deveRejeitarNumeroIncorretoDeParametrosRegra2() {
            // Arrange
            String[] params = {"1", "5"}; // Falta o parâmetro q

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarParametrosRegra(params, 2, 3, false));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Formato inválido para Regra 2"));
            assertTrue(exception.getMessage().contains("'1 t q'"));
            assertTrue(exception.getMessage().contains("3 parâmetros"));
        }

        @Test
        @DisplayName("Deve rejeitar quando t não é um número")
        void deveRejeitarQuandoTNaoENumero() {
            // Arrange
            String[] params = {"0", "cinco", "10", "15"};

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarParametrosRegra(params, 1, 4, true));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Os parâmetros t, q e i devem ser números válidos"));
        }

        @Test
        @DisplayName("Deve rejeitar quando q não é um número")
        void deveRejeitarQuandoQNaoENumero() {
            // Arrange
            String[] params = {"0", "5", "dez", "15"};

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarParametrosRegra(params, 1, 4, true));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Os parâmetros t, q e i devem ser números válidos"));
        }

        @Test
        @DisplayName("Deve rejeitar quando i não é um número para Regra 1")
        void deveRejeitarQuandoINaoENumero() {
            // Arrange
            String[] params = {"0", "5", "10", "quinze"};

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarParametrosRegra(params, 1, 4, true));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Os parâmetros t, q e i devem ser números válidos"));
        }

        @Test
        @DisplayName("Deve ter mensagem de erro diferente quando não validar i")
        void deveTerMensagemErroSemIQuandoNaoValidarI() {
            // Arrange
            String[] params = {"1", "cinco", "10"};

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarParametrosRegra(params, 2, 3, false));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Os parâmetros t e q devem ser números válidos"));
        }
    }

    @Nested
    @DisplayName("Testes para validarIndices")
    class ValidarIndicesTest {

        @ParameterizedTest
        @DisplayName("Deve aceitar índices válidos")
        @CsvSource({
                "0, 0",
                "0, 99",
                "50, 50",
                "10, 99",
                "0, 50"
        })
        void deveAceitarIndicesValidos(int t, int q) {
            // Act & Assert
            assertDoesNotThrow(() -> ValidationUtil.validarIndices(t, q));
        }

        @ParameterizedTest
        @DisplayName("Deve rejeitar quando t é maior que 99")
        @ValueSource(ints = {100, 101, 999, Integer.MAX_VALUE})
        void deveRejeitarTMaiorQue99(int t) {
            // Arrange
            int q = 99;

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarIndices(t, q));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Índice t deve estar entre 0 e 99"));
            assertTrue(exception.getMessage().contains("mas foi: " + t));
        }

        @ParameterizedTest
        @DisplayName("Deve rejeitar quando q é maior que 99")
        @ValueSource(ints = {100, 101, 999, Integer.MAX_VALUE})
        void deveRejeitarQMaiorQue99(int q) {
            // Arrange
            int t = 0;

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarIndices(t, q));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Índice q deve estar entre 0 e 99"));
            assertTrue(exception.getMessage().contains("mas foi: " + q));
        }

        @ParameterizedTest
        @DisplayName("Deve rejeitar quando t é negativo")
        @ValueSource(ints = {-1, -5, -100, Integer.MIN_VALUE})
        void deveRejeitarTNegativo(int t) {
            // Arrange
            int q = 10;

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarIndices(t, q));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Índice t deve estar entre 0 e 99"));
            assertTrue(exception.getMessage().contains("mas foi: " + t));
        }

        @ParameterizedTest
        @DisplayName("Deve rejeitar quando q é negativo")
        @ValueSource(ints = {-1, -5, -100, Integer.MIN_VALUE})
        void deveRejeitarQNegativo(int q) {
            // Arrange
            int t = 0;

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarIndices(t, q));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Índice q deve estar entre 0 e 99"));
            assertTrue(exception.getMessage().contains("mas foi: " + q));
        }

        @Test
        @DisplayName("Deve rejeitar quando t é maior que q")
        void deveRejeitarTMaiorQueQ() {
            // Arrange
            int t = 10;
            int q = 5;

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtil.validarIndices(t, q));

            // Verificar mensagem de erro
            assertTrue(exception.getMessage().contains("Índice t (10) deve ser menor ou igual ao índice q (5)"));
        }
    }
}