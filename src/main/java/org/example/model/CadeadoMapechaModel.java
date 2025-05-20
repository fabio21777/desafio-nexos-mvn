package org.example.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Modelo que representa o estado do Cadeado Mapecha.
 * Contém um array de inteiros e um conjunto de números impressos.
 *
 *
 * */

public class CadeadoMapechaModel {
    /**
     * Array que armazena os números do cadeado.
     * */
    private final int[] array = new int[100];
    /**
     * Conjunto que armazena os números que já foram impressos.
     * */
    private final Set<Integer> numerosImpressos = new HashSet<>();


    public int[] getArray() {
        return array;
    }

    public Set<Integer> getNumerosImpressos() {
        return numerosImpressos;
    }

    /**
     * Zera todos os valores do array, definindo-os como 0.
     * */
    public void zerarArray() {
        Arrays.fill(array, 0);
    }
}
