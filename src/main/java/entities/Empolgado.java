package entities;

import interfaces.ITipoCliente;

import java.io.Serializable;

public class Empolgado implements ITipoCliente, Serializable {
    private static final long serialVersionUID = 1L;
    static final double DESCONTO_EMPOLGADO = 0.1;
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
