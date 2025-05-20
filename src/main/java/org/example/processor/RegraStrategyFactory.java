package org.example.processor;

/**
 * Fábrica para criar estratégias de regras do sistema Cadeado Mapecha.
 * Implementa o padrão Factory Method para instanciar a estratégia adequada.
 */
public class RegraStrategyFactory {
    /**
     * Cria uma estratégia baseada no tipo de regra especificado.
     *
     * @param tipo O tipo de regra que determina qual estratégia criar
     * @return Uma instância da estratégia correspondente ao tipo
     * @throws IllegalArgumentException Se o tipo de regra não for suportado
     */
    public static RegraStrategy criarEstrategia(TipoRegra tipo) {
        switch (tipo) {
            case REGRA1:
                return new Regra1Strategy();
            case REGRA2:
                return new Regra2Strategy();
            default:
                throw new IllegalArgumentException("Tipo de regra não suportado: " + tipo);
        }
    }
}