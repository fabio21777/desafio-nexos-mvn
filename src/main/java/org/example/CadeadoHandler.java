package org.example;

import org.example.ci.CadeadoCLI;
import org.example.ci.EntradaStrategy;
import org.example.ci.EntradaStrategyFactory;
import org.example.ci.ModoEntrada;
import org.example.model.CadeadoMapechaModel;
import org.example.processor.CadeadoProcessor;

import java.util.Scanner;

/**
 * Manipulador principal do sistema Cadeado Mapecha.
 * Coordena a interação entre CLI, processador e resultados.
 */
public class CadeadoHandler {
    /** Processador responsável pela execução de comandos */
    private CadeadoProcessor processor;

    /** Interface de linha de comando para interação com usuário */
    private CadeadoCLI cli;

    /** Controla o loop principal de execução */
    private boolean executando = true;

    /** modelo para resultados */
    CadeadoMapechaModel modelo = new CadeadoMapechaModel();

    /**
     * Cria um novo manipulador com o processador e CLI fornecidos.
     *
     * @param processor Processador de comandos
     * @param cli Interface de linha de comando
     */
    public CadeadoHandler(CadeadoProcessor processor, CadeadoCLI cli) {
        this.processor = processor;
        this.cli = cli;
    }

    /**
     * Inicia o loop principal do programa.
     * Executa o fluxo e trata exceções até que seja finalizado.
     */
    public void iniciar() {
        while (executando) {
            try {
                executarFluxoPrincipal();
                perguntarContinuar("Processamento concluído com sucesso!");
            } catch (Exception e) {
                perguntarContinuar("Ocorreu um erro durante o processamento: " + e.getMessage());
            }
        }
    }

    /**
     * Executa o fluxo principal: seleção de modo e processamento.
     */
    private void executarFluxoPrincipal() {
        int codigoModo = cli.exibirMenuEObterModo();
        ModoEntrada modoEntrada = ModoEntrada.fromCodigo(codigoModo);
        EntradaStrategy estrategia = EntradaStrategyFactory.criarEstrategia(modoEntrada);
        estrategia.processar(this, cli);
    }

    /**
     * Exibe mensagem e pergunta se usuário deseja continuar ou sair.
     *
     * @param mensagem Mensagem a ser exibida
     */
    private void perguntarContinuar(String mensagem) {
        cli.mostrarMensagem(mensagem);

        System.out.println("\nDeseja voltar ao menu principal ou sair?");
        System.out.println("1 - Voltar ao menu principal");
        System.out.println("2 - Sair do programa");
        System.out.print("Escolha uma opção: ");

        Scanner scanner = new Scanner(System.in);
        int opcao = 1; // Por padrão volta ao menu

        try {
            opcao = scanner.nextInt();
        } catch (Exception ex) {
            // Se houver erro na leitura, assume opção 1 (voltar ao menu)
        }

        if (opcao == 2) {
            finalizarExecucao();
        } else {
            // Resetar o processor e o modelo para um estado limpo
            processor = new CadeadoProcessor();
            modelo = new CadeadoMapechaModel();
            System.out.println("\nReiniciando o programa...\n");
        }
    }

    /**
     * Finaliza a execução do programa.
     */
    private void finalizarExecucao() {
        executando = false;
        cli.mostrarMensagemFinal();
        cli.fechar();
    }

    /**
     * Processa uma linha de comando usando o processador.
     *
     * @param linha Linha de comando a ser processada
     */
    public void processarLinha(String linha) {
        processor.processarComando(linha, modelo);
    }
}