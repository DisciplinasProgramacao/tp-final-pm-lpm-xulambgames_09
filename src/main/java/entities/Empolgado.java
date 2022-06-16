package entities;

import interfaces.ITipoCliente;

public class Empolgado implements ITipoCliente {
    static final double DESCONTO_EMPOLGADO = 0.9;
    static final int VALOR_MENSALIDADE_EMPOLGADO = 10;

    @Override
    public double porcentagemDesconto() {
        return DESCONTO_EMPOLGADO;
    }

    @Override
    public int valorMensalidade() {
        return VALOR_MENSALIDADE_EMPOLGADO;
    }
}
