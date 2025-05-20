package org.example.ci;

import org.example.CadeadoHandler;

import java.util.List;

import java.util.List;

/**
 * Implementação da estratégia de entrada que processa texto completo de uma vez.
 *
 * Esta classe implementa a interface {@link EntradaStrategy} e fornece uma estratégia
 * específica para processar a entrada do usuário em massa, permitindo que o usuário
 * cole um bloco inteiro de texto que contém múltiplos comandos para serem processados
 * sequencialmente.
 *
 * Esta estratégia é útil quando o usuário já possui todos os comandos preparados
 */
class TextoEntradaStrategy implements EntradaStrategy {

    /**
     * Processa a entrada do usuário em formato de texto completo.
     *
     * Utiliza o método {@link CadeadoCLI#lerTodasAsLinhas()} para obter todas as linhas
     * de texto inseridas pelo usuário e processa cada linha individualmente, ignorando
     * linhas vazias.
     *
     * @param handler O manipulador de cadeado que irá processar cada linha de comando
     * @param cli A interface de linha de comando que será utilizada para interação com o usuário
     */
    @Override
    public void processar(CadeadoHandler handler, CadeadoCLI cli) {
        List<String> linhas = cli.lerTodasAsLinhas();

        for (String linha : linhas) {
            // Se a linha for vazia, ignora ela e continua
            if (linha.trim().isEmpty()) {
                continue;
            }
            handler.processarLinha(linha);
        }
    }
}

