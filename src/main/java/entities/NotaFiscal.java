package entities;

import java.io.Serializable;
import java.time.LocalDate;

public class NotaFiscal implements Serializable {
    private static final long serialVersionUID = 1L;

    public Cliente cliente;
    public LocalDate dataPagamento;
    public double valorPago;

    @Override
    public String toString() {
        return this.cliente + "\nData do pagamento: " + this.dataPagamento + "\nValor Pago: " + Compra.formatoDinheiroBrasileiro(this.valorPago);
    }
}
