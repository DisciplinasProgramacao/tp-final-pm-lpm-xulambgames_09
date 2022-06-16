package enums;

import entities.Compra;

public enum DescontoCompra {
    DESC_VINTE(0.2),
    DESC_DEZ(0.1);

    double pctDesconto;

    DescontoCompra(double pctDesconto) {
        this.pctDesconto = pctDesconto;
    }

    public double getPctDesconto() {
        return pctDesconto;
    }

    public double descontoParaAplicar(Compra compra) {
        return 0.0;
    }
}
