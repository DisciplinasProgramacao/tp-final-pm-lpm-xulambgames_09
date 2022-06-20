package entities;

import enums.DescontoCompra;
import interfaces.ITipoCliente;

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
        this.incluirJogo(jogo);
        this.descontoCompra = null;
    }

    public LocalDate getData() {
        return this.dataCompra;
    }

    public Optional<DescontoCompra> getDescontoCompra() {
        return Optional.ofNullable(descontoCompra);
    }

    public List<Jogo> getJogos(){
        return this.jogos;
    }

    public void incluirJogo(Jogo novoJogo, double pctDesconto) {
        novoJogo.descontoValido(pctDesconto);
        this.jogos.add(novoJogo);
        atualizarDescontoCompra();
    }

    public void incluirJogo(Jogo itemAtual) {
        this.jogos.add(itemAtual);
        atualizarDescontoCompra();
    }

    private void atualizarDescontoCompra() {
        this.descontoCompra = DescontoCompra.descontoParaAplicar(this);
    }

    public double valorTotalJogos() {
        double valor=0d;

        for (Jogo jogo : this.jogos) {
            valor += jogo.getPrecoBase();
        }

        return valor;
    }

    public double valorAPagarJogos() {
        double valor=0d;

        for (Jogo jogo : this.jogos) {
            valor += jogo.precoFinal();
        }

        return valor;
    }

    public double valorAPagar() {
        double valorTotal = valorAPagarJogos();
        double valorComDescontoCompra = valorTotal - (valorTotal * this.getDescontoCompra().map(DescontoCompra::getPctDesconto).orElse(0d));


        return valorComDescontoCompra - (valorComDescontoCompra * this.cliente.getTipoCliente().map(ITipoCliente::porcentagemDesconto).orElse(0d));
    }

    public String relatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("Compra do cliente ").append(this.cliente.getNome()).append(" no dia ").append(this.dataCompra.toString()).append("\n");
        sb.append("Valor total: ").append(formatoDinheiroBrasileiro(this.valorTotalJogos())).append("\n");
        this.getDescontoCompra().ifPresentOrElse(
                d -> sb.append("Desconto da compra: ").append(d.getPctDesconto()).append("%\n")
                , () -> sb.append("Sem desconto de compra\n")
        );
        sb.append("Valor a pagar: " + formatoDinheiroBrasileiro(this.valorAPagar()) + "\n");
        sb.append("Itens:\n");
        for (Jogo jogo : this.jogos) {
            sb.append(jogo.toString() + "\n");
        }
        return sb.toString();
    }

    public static String formatoDinheiroBrasileiro(double valor) {
        return String.format("R$ %.2f", valor);
    }

    @Override
    public int compareTo(Compra o) {
        if(this.valorTotalJogos()> o.valorTotalJogos()) return 1;
        else if(this.valorTotalJogos() < o.valorTotalJogos()) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "Nome do cliente: " + this.cliente.getNome() + " - " + this.dataCompra + " - " + this.valorTotalJogos();
    }
}
