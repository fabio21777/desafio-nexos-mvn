package org.example.processor;

import org.example.model.CadeadoMapechaModel;

/**
 * Interface para as estratégias de regras do sistema Cadeado Mapecha.
 *
 * Na versão atual, possui duas implementações:
 * - Regra1Strategy: adiciona um valor a um intervalo do array
 * - Regra2Strategy: soma valores em um intervalo, imprime e zera o array
 */
public interface RegraStrategy {
    /**
     * Executa a regra com os parâmetros fornecidos.
     *
     * @param parametros Array com os parâmetros do comando
     * @param processor Processador que invocou a estratégia
     * @param modelo Modelo de dados a ser manipulado
     */
    void executar(String[] parametros, CadeadoProcessor processor, CadeadoMapechaModel modelo);
}