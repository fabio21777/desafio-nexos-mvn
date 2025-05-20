package org.example.ci;

import org.example.CadeadoHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de linha de comando para o Sistema de Cadeado Mapecha.
 * Esta classe fornece métodos para interação do usuário através do console,
 * incluindo exibição de menus, leitura de entrada e processamento de comandos.
 */
public class CadeadoCLI {
    /** Scanner utilizado para ler entrada do usuário. */
    private Scanner scanner;

    /**
     * Constrói uma nova instância de CadeadoCLI.
     * Inicializa um Scanner para ler a entrada do usuário do console.
     */
    public CadeadoCLI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Exibe o menu principal e solicita ao usuário que selecione um modo de entrada.
     *
     * @return O código do modo de entrada selecionado (inteiro)
     */
    public int exibirMenuEObterModo() {
        System.out.println("=== Sistema de Cadeado Mapecha ===");
        System.out.println("Escolha o modo de entrada:");
        System.out.println(ModoEntrada.TEXTO.getCodigo() + " - Copiar e colar todo o texto");
        System.out.println(ModoEntrada.LINHA.getCodigo() + " - Digitar linha por linha");
        System.out.print("Digite o número do modo: ");

        int modo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer
        return modo;
    }

    /**
     * Lê múltiplas linhas de texto de entrada do usuário até que a string de terminação "FIM" seja inserida.
     *
     * @return Uma lista contendo todas as linhas de entrada
     */
    public List<String> lerTodasAsLinhas() {
        System.out.println("Cole todo o texto abaixo (certifique-se de terminar com FIM):");

        List<String> linhas = new ArrayList<>();
        String linha;
        while (true) {
            linha = scanner.nextLine().trim(); // lê até pressionar Enter
            if (linha.equalsIgnoreCase("FIM")) {
                break; // encerra apenas se for exatamente "FIM"
            }
            if (!linha.isEmpty()) {
                linhas.add(linha);
            }
        }

        System.out.println("Processando todas as linhas...");
        return linhas;
    }


    /**
     * Processa a entrada linha por linha usando o handler fornecido até que a string de terminação "FIM" seja inserida.
     *
     * @param handler O CadeadoHandler para processar cada linha de entrada
     */
    public void processarEntradaLinhaPorLinha(CadeadoHandler handler) {
        System.out.println("Digite os comandos um por um (FIM para terminar):");

        String linha;
        while (!(linha = scanner.nextLine()).equalsIgnoreCase("FIM")) {
            handler.processarLinha(linha);
        }
    }

    /**
     * Exibe uma mensagem de conclusão após o processamento ser finalizado.
     */
    public void mostrarMensagemFinal() {
        System.out.println("----------------------------------");
        System.out.println("Processamento concluído!");
    }

    /**
     * Exibe uma mensagem de erro ao usuário.
     *
     * @param mensagem A mensagem de erro a ser exibida
     */
    public void mostrarErro(String mensagem) {
        System.out.println("Erro: " + mensagem);
    }

    /**
     * Fecha o scanner e libera recursos.
     * Deve ser chamado quando a CLI não for mais necessária.
     */
    public void fechar() {
        scanner.close();
    }

    /**
     * Exibe uma mensagem ao usuário.
     *
     * @param mensagem A mensagem a ser exibida
     */
    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}