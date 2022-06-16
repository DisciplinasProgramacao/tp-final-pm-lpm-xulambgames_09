package entities;

import interfaces.ITipoCliente;

public class Fanatico implements ITipoCliente {
    static final double DESCONTO_FANATICO = 0.7;
    static final int VALOR_MENSALIDADE_FANATICO = 25;

    @Override
    public double porcentagemDesconto() {
        return DESCONTO_FANATICO;
    }

    @Override
    public int valorMensalidade() {
        return VALOR_MENSALIDADE_FANATICO;
    }
}
