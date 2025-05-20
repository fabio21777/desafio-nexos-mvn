package org.example.ci;

import org.example.CadeadoHandler;

/**
 * Interface EntradaStrategy
 *
 * Esta interface define o contrato para as estratégias de entrada no sistema Cadeado.
 * As implementações desta interface devem fornecer a lógica necessária para processar
 * as entradas de dados, dependendo do modo de entrada selecionado.
 *
 * Na versão atual, existem duas implementações disponíveis:
 * <ul>
 *   <li>TEXTO - Permite ao usuário copiar e colar todo o texto de uma vez</li>
 *   <li>LINHA - Permite ao usuário digitar linha por linha</li>
 * </ul>
 *
 * O padrão Strategy é utilizado para permitir que diferentes métodos de entrada
 * possam ser implementados e trocados de forma flexível sem alterar o código cliente.
 */
public interface EntradaStrategy {

    /**
     * Processa a entrada do usuário de acordo com a estratégia implementada.
     *
     * @param handler O manipulador de cadeado que irá processar cada linha de comando
     * @param cli A interface de linha de comando que será utilizada para interação com o usuário
     */
    void processar(CadeadoHandler handler, CadeadoCLI cli);
}