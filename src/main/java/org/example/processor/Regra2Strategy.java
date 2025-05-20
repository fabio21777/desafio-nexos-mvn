package org.example.processor;

import org.example.model.CadeadoMapechaModel;

/**
 * Estratégia para a Regra 2 do sistema Cadeado Mapecha.
 * Soma valores em um intervalo, imprime o resultado e zera o array.
 */
class Regra2Strategy implements RegraStrategy {
    /**
     * Executa a regra 2: soma valores no intervalo [t,q], imprime e zera o array.
     *
     * @param parametros Array com os parâmetros do comando
     * @param processor Processador que invocou a estratégia
     * @param modelo Modelo de dados a ser manipulado
     */
    @Override
    public void executar(String[] parametros, CadeadoProcessor processor, CadeadoMapechaModel modelo) {
        ValidationUtil.validarParametrosRegra(parametros, 2, 3, false);
        int t = Integer.parseInt(parametros[1]);
        int q = Integer.parseInt(parametros[2]);

        somarEImprimirIntervalo(t, q, modelo);
    }

    /**
     * Soma valores no intervalo, imprime (se for novo) e zera o array.
     *
     * @param t Índice inicial do intervalo
     * @param q Índice final do intervalo
     * @param modelo Modelo contendo o array e controle de impressões
     */
    private void somarEImprimirIntervalo(int t, int q, CadeadoMapechaModel modelo) {
        int soma = 0;
        for (int indice = t; indice <= q; indice++) {
            soma += modelo.getArray()[indice];
        }

        if (!modelo.getNumerosImpressos().contains(soma)) {
            System.out.println(soma);
            modelo.getNumerosImpressos().add(soma);
        }
        modelo.zerarArray();
    }
}