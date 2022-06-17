package entities;

import enums.DescontoCompra;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Compra implements Serializable, Comparable<Compra> {
    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private LocalDate dataCompra;
    private DescontoCompra descontoCompra;
    private List<Jogo> jogos;

    public Compra(Cliente cliente, LocalDate dataCompra, Jogo jogo) {
        this.cliente = cliente;
        this.dataCompra = dataCompra;
        this.jogos = new LinkedList<>();
        this.addItem(jogo);
        this.descontoCompra = null;
    }

    public void addItem(Jogo itemAtual, double pctDesconto) {
        itemAtual.descontoValido(pctDesconto);
        this.jogos.add(itemAtual);
        atualizarDescontoCompra();
    }

    public void addItem(Jogo itemAtual) {
        this.jogos.add(itemAtual);
        atualizarDescontoCompra();
    }

    public LocalDate getData() {
        return this.dataCompra;
    }

    private void atualizarDescontoCompra() {
        this.descontoCompra = DescontoCompra.descontoParaAplicar(this);
    }

    @Override
    public int compareTo(Compra o) {
        if(this.valorTotal()> o.valorTotal()) return 1;
        else if(this.valorTotal() < o.valorTotal()) return -1;
        return 0;
    }

    public double valorTotal() {
        double valor=0d;

        for (Jogo jogo : this.jogos) {
            valor += jogo.precoFinal();
        }

        return valor;
    }

    public double getValorPago() {
        return 0;
    }

    public String relatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("Compra do cliente " + this.cliente.getNome() + " no dia " + this.dataCompra.toString() + "\n");
        sb.append("Valor total: " + this.valorTotal() + "\n");
        sb.append("Desconto: " + Optional.ofNullable(this.descontoCompra).map(DescontoCompra::getPctDesconto).orElse(null) + "%\n");
        sb.append("Valor a pagar: " + this.getValorPago() + "\n");
        sb.append("Itens:\n");
        for (Jogo jogo : this.jogos) {
            sb.append(jogo.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Compra: " + this.cliente.getNome() + " - " + this.dataCompra + " - " + this.valorTotal();
    }
}
