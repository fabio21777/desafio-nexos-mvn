package org.example.processor;

import org.example.model.CadeadoMapechaModel;

/**
 * Processador de comandos para o sistema Cadeado Mapecha.
 * Interpreta comandos de entrada e delega execução para a estratégia adequada.
 */
public class CadeadoProcessor {

    /**
     * Processa um comando e aplica a regra correspondente no modelo.
     *
     * @param comando Comando a ser processado
     * @param modelo Modelo de dados a ser manipulado
     */
    public void processarComando(String comando, CadeadoMapechaModel modelo) {
        String[] partes = comando.split(" ");
        String codigoRegra = partes[0];

        TipoRegra tipoRegra = TipoRegra.fromCodigo(codigoRegra);
        RegraStrategy estrategia = RegraStrategyFactory.criarEstrategia(tipoRegra);
        estrategia.executar(partes, this, modelo);
    }
}