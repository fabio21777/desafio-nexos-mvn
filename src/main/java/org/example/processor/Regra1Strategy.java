package org.example.processor;

import org.example.model.CadeadoMapechaModel;

/**
 * Estratégia para a Regra 1 do sistema Cadeado Mapecha.
 * Adiciona um valor em um intervalo específico do array.
 */
class Regra1Strategy implements RegraStrategy {
    /**
     * Executa a regra 1: adiciona um valor i a cada posição no intervalo [t,q].
     *
     * @param parametros Array com os parâmetros do comando
     * @param processor Processador que invocou a estratégia
     * @param modelo Modelo de dados a ser manipulado
     */
    @Override
    public void executar(String[] parametros, CadeadoProcessor processor, CadeadoMapechaModel modelo) {
        ValidationUtil.validarParametrosRegra(parametros, 1, 4, true);
        int t = Integer.parseInt(parametros[1]);
        int q = Integer.parseInt(parametros[2]);
        int i = Integer.parseInt(parametros[3]);
        adicionarValorNoIntervalo(t, q, i, modelo);
    }

    /**
     * Adiciona o valor i a cada posição no intervalo [t,q] do array.
     *
     * @param t Índice inicial do intervalo
     * @param q Índice final do intervalo
     * @param i Valor a ser adicionado
     * @param modelo Modelo contendo o array a ser modificado
     */
    private void adicionarValorNoIntervalo(int t, int q, int i, CadeadoMapechaModel modelo) {
        for (int indice = t; indice <= q; indice++) {
            modelo.getArray()[indice] += i;
        }
    }
}