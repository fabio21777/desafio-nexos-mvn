package org.example.processor;

/**
 * Enum que representa os tipos de regras do sistema Cadeado Mapecha.
 * Cada regra é identificada por um código único.
 */
public enum TipoRegra {
    /** Regra 1: Adiciona valor em um intervalo. Código: "0" */
    REGRA1("0"),

    /** Regra 2: Soma valores em intervalo, imprime e zera. Código: "1" */
    REGRA2("1");

    /** Código que identifica a regra nos comandos de entrada */
    private final String codigo;

    /**
     * Constrói um TipoRegra com o código especificado.
     *
     * @param codigo Código identificador da regra
     */
    TipoRegra(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o código da regra.
     *
     * @return Código como string
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Converte um código em string para o TipoRegra correspondente.
     *
     * @param codigo Código da regra a ser convertido
     * @return Instância de TipoRegra correspondente ao código
     * @throws IllegalArgumentException Se o código não corresponder a nenhuma regra
     */
    public static TipoRegra fromCodigo(String codigo) {
        for (TipoRegra tipo : values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de regra inválido: " + codigo);
    }
}