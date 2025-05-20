package org.example.ci;

import org.example.CadeadoHandler;

/**
 * Implementação da estratégia de entrada que processa os comandos linha por linha.
 *
 * Esta classe implementa a interface {@link EntradaStrategy} e fornece uma estratégia
 * específica para processar a entrada do usuário de forma interativa, permitindo
 * que o usuário insira um comando por vez e receba feedback imediato após cada entrada.
 *
 * Esta estratégia é útil quando o usuário precisa inserir comandos de forma incremental
 * ou quando deseja ver os resultados de cada comando antes de prosseguir para o próximo.
 */
class LinhaEntradaStrategy implements EntradaStrategy {

    /**
     * Processa a entrada do usuário linha por linha.
     *
     * Utiliza o método {@link CadeadoCLI#processarEntradaLinhaPorLinha(CadeadoHandler)}
     * para solicitar e processar cada linha de comando individualmente até que o usuário
     * insira a string de terminação "FIM".
     *
     * @param handler O manipulador de cadeado que irá processar cada linha de comando
     * @param cli A interface de linha de comando que será utilizada para interação com o usuário
     */
    @Override
    public void processar(CadeadoHandler handler, CadeadoCLI cli) {
        cli.processarEntradaLinhaPorLinha(handler);
    }
}