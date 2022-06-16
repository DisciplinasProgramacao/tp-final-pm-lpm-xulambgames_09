package enums;

public enum JogoCategoria {
    LANCAMENTO(0.1, 0.1),
    PREMIUM(0.0, 0.0),
    REGULAR(0.7, 1.0),
    PROMOCAO(0.3, 0.5);

    double pctDescontoMax;
    double pctDescontoMin;

    JogoCategoria(double pctDescontoMax, double pctDescontoMin) {
        this.pctDescontoMax = pctDescontoMax;
        this.pctDescontoMin = pctDescontoMin;
    }

    public double porcentagemDesconto() {
        return 0.0;
    }
}
