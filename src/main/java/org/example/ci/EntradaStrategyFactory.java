package org.example.ci;


// Factory para criar estratégias de entrada
public class EntradaStrategyFactory {
    public static EntradaStrategy criarEstrategia(ModoEntrada modo) {
        return switch (modo) {
            case TEXTO -> new TextoEntradaStrategy();
            case LINHA -> new LinhaEntradaStrategy();
            default -> throw new IllegalArgumentException("Modo de entrada não suportado: " + modo);
        };
    }
}
