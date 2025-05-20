package org.example.processor;

// Classe utilitária para validações
public class ValidationUtil {

    /**
     * Valida os parâmetros genéricos para as regras do cadeado
     *
     * @param parametros Array de parâmetros
     * @param regraNumero Número da regra (1 ou 2)
     * @param parametrosEsperados Número de parâmetros esperados
     * @param validarParametroI Se deve validar o parâmetro i (regra 1)
     */
    public static void validarParametrosRegra(String[] parametros, int regraNumero,
                                              int parametrosEsperados, boolean validarParametroI) {
        // Verificar número de parâmetros
        if (parametros.length != parametrosEsperados) {
            String formatoEsperado = regraNumero == 1 ? "'0 t q i'" : "'1 t q'";
            throw new IllegalArgumentException(
                    "Formato inválido para Regra " + regraNumero +
                            ". Esperado: " + formatoEsperado + " com " + parametrosEsperados +
                            " parâmetros, mas recebido " + parametros.length + " parâmetros");
        }

        try {
            // Validar se são números
            int t = Integer.parseInt(parametros[1]);
            int q = Integer.parseInt(parametros[2]);

            // Validar parâmetro i para Regra 1
            if (validarParametroI && parametros.length > 3) {
                Integer.parseInt(parametros[3]);
            }

            // Validar índices
            validarIndices(t, q);

        } catch (NumberFormatException e) {
            String mensagem = validarParametroI ?
                    "Os parâmetros t, q e i devem ser números válidos" :
                    "Os parâmetros t e q devem ser números válidos";

            throw new IllegalArgumentException(mensagem);
        }
    }

    /**
     * Valida os índices t e q
     */
    public static void validarIndices(int t, int q) {
        // Verificar limites
        if (t >= 100 || t < 0) {
            throw new IllegalArgumentException(
                    "Índice t deve estar entre 0 e 99, mas foi: " + t);
        }

        if (q >= 100 || q < 0) {
            throw new IllegalArgumentException(
                    "Índice q deve estar entre 0 e 99, mas foi: " + q);
        }

        // Verificar faixa válida
        if (t > q) {
            throw new IllegalArgumentException(
                    "Índice t (" + t + ") deve ser menor ou igual ao índice q (" + q + ")");
        }
    }
}