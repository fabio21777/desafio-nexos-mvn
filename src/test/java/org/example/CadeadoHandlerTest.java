package org.example;

import org.example.ci.CadeadoCLI;
import org.example.ci.ModoEntrada;
import org.example.model.CadeadoMapechaModel;
import org.example.processor.CadeadoProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Testes do CadeadoHandler")
class CadeadoHandlerTest {

    private CadeadoCLI mockCli;
    private CadeadoProcessor mockProcessor;
    private CadeadoHandler handler;
    private CadeadoMapechaModel modelo;

    @BeforeEach
    void setUp() {
        // Inicializar mocks manualmente
        mockCli = mock(CadeadoCLI.class);
        mockProcessor = mock(CadeadoProcessor.class);
        handler = new CadeadoHandler(mockProcessor, mockCli);
        modelo = new CadeadoMapechaModel();

        // Configurar o processador para usar uma implementação real em vez de um mock
        // para alguns testes que precisam verificar o estado do modelo
        doCallRealMethod().when(mockProcessor).processarComando(anyString(), eq(modelo));
    }

    @Test
    @DisplayName("Teste de sanidade: criação do handler")
    void testSanidade() {
        // Verificar se o handler é criado corretamente
        assertNotNull(handler);
    }

    @Test
    @DisplayName("Verifica se uma linha de comando é processada corretamente")
    void testProcessarLinha() {
        // Arrange
        String linha = "0 1 2 30";

        // Act
        handler.processarLinha(linha);

        // Assert - Verificar se o processador foi chamado com os parâmetros corretos
        verify(mockProcessor, times(1)).processarComando(eq(linha), any(CadeadoMapechaModel.class));
    }

    @Nested
    @DisplayName("Testes com Resultados Verificáveis")
    class ResultadosVerificaveisTests {

        @Test
        @DisplayName("Verifica o conjunto de números impressos após processamento completo")
        void testVerificacaoNumerosImpressos() throws Exception {
            // Arrange - Configurar um processador real para processar comandos
            CadeadoProcessor processorReal = new CadeadoProcessor();
            CadeadoHandler handlerReal = new CadeadoHandler(processorReal, mockCli);

            // Configurar o modelo através de reflection
            Field modeloField = handlerReal.getClass().getDeclaredField("modelo");
            modeloField.setAccessible(true);
            CadeadoMapechaModel modeloReal = (CadeadoMapechaModel) modeloField.get(handlerReal);

            // Processar o exemplo do cadeado
            handlerReal.processarLinha("0 2 4 26");
            handlerReal.processarLinha("0 4 8 80");
            handlerReal.processarLinha("0 4 5 20");
            handlerReal.processarLinha("1 8 8");
            handlerReal.processarLinha("0 2 4 26");
            handlerReal.processarLinha("0 4 8 80");
            handlerReal.processarLinha("0 4 5 20");
            handlerReal.processarLinha("1 8 8");

            // Acessar conjunto de números impressos
            Field numerosImpressosField = modeloReal.getClass().getDeclaredField("numerosImpressos");
            numerosImpressosField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Set<Integer> numerosImpressos = (Set<Integer>) numerosImpressosField.get(modeloReal);

            // Assert - Verificar que apenas 80 foi impresso
            assertEquals(1, numerosImpressos.size());
            assertTrue(numerosImpressos.contains(80));
        }

        @Test
        @DisplayName("Verifica o estado do array após processamento parcial")
        void testEstadoArrayAposProcessamento() throws Exception {
            // Arrange - Configurar um processador real
            CadeadoProcessor processorReal = new CadeadoProcessor();
            CadeadoHandler handlerReal = new CadeadoHandler(processorReal, mockCli);

            // Configurar o modelo através de reflection
            Field modeloField = handlerReal.getClass().getDeclaredField("modelo");
            modeloField.setAccessible(true);
            CadeadoMapechaModel modeloReal = (CadeadoMapechaModel) modeloField.get(handlerReal);

            // Act - Processar apenas os três primeiros comandos
            handlerReal.processarLinha("0 2 4 26");
            handlerReal.processarLinha("0 4 8 80");
            handlerReal.processarLinha("0 4 5 20");

            // Acessar array do modelo
            Field arrayField = modeloReal.getClass().getDeclaredField("array");
            arrayField.setAccessible(true);
            int[] array = (int[]) arrayField.get(modeloReal);

            // Assert - Verificar estado do array após os três comandos
            assertAll(
                    () -> assertEquals(0, array[0], "Posição 0 deve ser 0"),
                    () -> assertEquals(0, array[1], "Posição 1 deve ser 0"),
                    () -> assertEquals(26, array[2], "Posição 2 deve ser 26"),
                    () -> assertEquals(26, array[3], "Posição 3 deve ser 26"),
                    () -> assertEquals(126, array[4], "Posição 4 deve ser 126 (26+80+20)"),
                    () -> assertEquals(100, array[5], "Posição 5 deve ser 100 (80+20)"),
                    () -> assertEquals(80, array[6], "Posição 6 deve ser 80"),
                    () -> assertEquals(80, array[7], "Posição 7 deve ser 80"),
                    () -> assertEquals(80, array[8], "Posição 8 deve ser 80")
            );
        }

        @Test
        @DisplayName("Verifica que o array é zerado após aplicar a regra 2")
        void testArrayZeradoAposRegra2() throws Exception {
            // Arrange - Configurar um processador real
            CadeadoProcessor processorReal = new CadeadoProcessor();
            CadeadoHandler handlerReal = new CadeadoHandler(processorReal, mockCli);

            // Configurar o modelo através de reflection
            Field modeloField = handlerReal.getClass().getDeclaredField("modelo");
            modeloField.setAccessible(true);
            CadeadoMapechaModel modeloReal = (CadeadoMapechaModel) modeloField.get(handlerReal);

            // Acessar array do modelo
            Field arrayField = modeloReal.getClass().getDeclaredField("array");
            arrayField.setAccessible(true);

            // Act - Processar comandos até a regra 2
            handlerReal.processarLinha("0 2 4 26");
            handlerReal.processarLinha("0 4 8 80");
            handlerReal.processarLinha("0 4 5 20");
            handlerReal.processarLinha("1 8 8"); // Regra 2 - deve zerar o array

            // Obter array após zerar
            int[] array = (int[]) arrayField.get(modeloReal);

            // Assert - Verificar que todas as posições estão zeradas
            for (int i = 0; i < array.length; i++) {
                assertEquals(0, array[i], "Posição " + i + " deve ser 0");
            }
        }

        @Test
        @DisplayName("Verifica o comportamento com vários valores diferentes impressos")
        void testMultiplosValoresImpressos() throws Exception {
            // Arrange - Configurar um processador real
            CadeadoProcessor processorReal = new CadeadoProcessor();
            CadeadoHandler handlerReal = new CadeadoHandler(processorReal, mockCli);

            // Configurar o modelo através de reflection
            Field modeloField = handlerReal.getClass().getDeclaredField("modelo");
            modeloField.setAccessible(true);
            CadeadoMapechaModel modeloReal = (CadeadoMapechaModel) modeloField.get(handlerReal);

            // Processar comandos para gerar múltiplos valores diferentes
            handlerReal.processarLinha("0 1 3 10");
            handlerReal.processarLinha("1 1 3"); // Deve imprimir 30

            handlerReal.processarLinha("0 2 4 20");
            handlerReal.processarLinha("1 2 4"); // Deve imprimir 60

            handlerReal.processarLinha("0 5 7 30");
            handlerReal.processarLinha("1 5 7"); // Deve imprimir 90

            // Tentar imprimir valor repetido
            handlerReal.processarLinha("0 1 3 10");
            handlerReal.processarLinha("1 1 3"); // Já imprimiu 30, não deve ser adicionado novamente

            // Acessar conjunto de números impressos
            Field numerosImpressosField = modeloReal.getClass().getDeclaredField("numerosImpressos");
            numerosImpressosField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Set<Integer> numerosImpressos = (Set<Integer>) numerosImpressosField.get(modeloReal);

            // Assert - Verificar que os três valores diferentes foram impressos
            Set<Integer> valoresEsperados = new HashSet<>(Arrays.asList(30, 60, 90));
            assertEquals(valoresEsperados, numerosImpressos);
        }
    }

    @Nested
    @DisplayName("Testes com Bloco de Texto")
    class BlocoDeTextoTests {
        @Test
        @DisplayName("Processa bloco de texto com linhas vazias e as ignora")
        void testBlocoDeTextoComLinhasVazias() {
            // Arrange
            when(mockCli.exibirMenuEObterModo()).thenReturn(1); // Modo TEXTO

            List<String> linhasEntrada = Arrays.asList(
                    "0 1 2 10",
                    "",  // Linha vazia deve ser ignorada
                    "0 3 4 20",
                    "   ",  // Linha com espaços deve ser ignorada
                    "1 1 4"
            );

            when(mockCli.lerTodasAsLinhas()).thenReturn(linhasEntrada);
            simulateUserInput("2");

            // Act
            handler.iniciar();

            // Assert - Verificar que apenas as linhas não vazias foram processadas
            verify(mockProcessor).processarComando(eq("0 1 2 10"), any(CadeadoMapechaModel.class));
            verify(mockProcessor).processarComando(eq("0 3 4 20"), any(CadeadoMapechaModel.class));
            verify(mockProcessor).processarComando(eq("1 1 4"), any(CadeadoMapechaModel.class));
            verify(mockProcessor, times(3)).processarComando(any(), any()); // Total: 3 linhas válidas
        }
    }

    @Nested
    @DisplayName("Testes com Entradas Inválidas")
    class EntradasInvalidasTests {

        @Test
        @DisplayName("Trata entrada com comando não reconhecido (nem 0 nem 1)")
        void testComandoNaoReconhecido() {
            // Arrange
            when(mockCli.exibirMenuEObterModo()).thenReturn(1); // Modo TEXTO

            List<String> linhasEntrada = Arrays.asList("9 1 2 3");
            when(mockCli.lerTodasAsLinhas()).thenReturn(linhasEntrada);

            // Configurar processador para simular erro no código de regra
            doThrow(new IllegalArgumentException("Código de regra inválido: 9"))
                    .when(mockProcessor).processarComando(eq("9 1 2 3"), any(CadeadoMapechaModel.class));

            simulateUserInput("2"); // Sair após erro

            // Act
            handler.iniciar();

            // Assert
            verify(mockCli).mostrarMensagem(contains("Código de regra inválido: 9"));
            verify(mockCli).mostrarMensagemFinal();
            verify(mockCli).fechar();
        }

        @Test
        @DisplayName("Trata entrada com índices fora do limite (maior que 99)")
        void testIndicesForaDoLimite() {
            // Arrange
            when(mockCli.exibirMenuEObterModo()).thenReturn(1); // Modo TEXTO

            List<String> linhasEntrada = Arrays.asList("0 100 200 30");
            when(mockCli.lerTodasAsLinhas()).thenReturn(linhasEntrada);

            // Configurar processador para simular erro de índice fora do limite
            doThrow(new IllegalArgumentException("Índice t deve estar entre 0 e 99, mas foi: 100"))
                    .when(mockProcessor).processarComando(eq("0 100 200 30"), any(CadeadoMapechaModel.class));

            simulateUserInput("2"); // Sair após erro

            // Act
            handler.iniciar();

            // Assert
            verify(mockCli).mostrarMensagem(contains("Índice t deve estar entre 0 e 99"));
            verify(mockCli).mostrarMensagemFinal();
            verify(mockCli).fechar();
        }
    }

    /**
     * Método auxiliar para simular entrada do usuário
     */
    private void simulateUserInput(String input) {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        // Não precisamos restaurar System.in pois cada teste cria uma nova instância
    }
}