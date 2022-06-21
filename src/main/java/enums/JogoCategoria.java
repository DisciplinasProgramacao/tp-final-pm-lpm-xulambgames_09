package enums;

public enum JogoCategoria {
    LANCAMENTO(0.1, 0.1),
    PREMIUM(0.0, 0.0),
    REGULAR(0.7, 1.0),
    PROMOCAO(0.3, 0.5);

    double pctDescontoMax;
    double pctDescontoMin;

    JogoCategoria(double pctDescontoMin, double pctDescontoMax) {
        this.pctDescontoMax = pctDescontoMax;
        this.pctDescontoMin = pctDescontoMin;
    }

    public boolean descontoValido(double pctDesconto) {
        return pctDesconto <= this.pctDescontoMax && pctDesconto >= this.pctDescontoMin || this.pctDescontoMax == this.pctDescontoMin;
    }

    public double getPctDescontoMax() {
        return pctDescontoMax;
    }

    public double getPctDescontoMin() {
        return pctDescontoMin;
    }

    @Override
    public String toString() {
        if (pctDescontoMax == pctDescontoMin && pctDescontoMax >= 0 && pctDescontoMin >= pctDescontoMin)
            return this.name() + " - Desconto = " + pctDescontoMax * 100 + "%";
        else
            return this.name() + " - Desconto Minimo = " + pctDescontoMin * 100 + "%" + ", Desconto Máximo = " + pctDescontoMax * 100 + "%";
    }
}
