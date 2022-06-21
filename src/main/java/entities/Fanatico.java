package entities;

import interfaces.ITipoCliente;

import java.io.Serializable;

public class Fanatico implements ITipoCliente, Serializable {
    private static final long serialVersionUID = 1L;
    static final double DESCONTO_FANATICO = 0.3;
    static final int VALOR_MENSALIDADE_FANATICO = 25;

    @Override
    public double porcentagemDesconto() {
        return DESCONTO_FANATICO;
    }

    @Override
    public int valorMensalidade() {
        return VALOR_MENSALIDADE_FANATICO;
    }

    @Override
    public String toString() {
        return "Fanatico";
    }
}
