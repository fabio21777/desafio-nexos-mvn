package org.example.ci;

// Enum para os modos de entrada
public enum ModoEntrada {
    TEXTO(1), LINHA(2);

    private final int codigo;

    ModoEntrada(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static ModoEntrada fromCodigo(int codigo) {
        for (ModoEntrada modo : values()) {
            if (modo.getCodigo() == codigo) {
                return modo;
            }
        }
        throw new IllegalArgumentException("Modo de entrada inválido: " + codigo);
    }
}