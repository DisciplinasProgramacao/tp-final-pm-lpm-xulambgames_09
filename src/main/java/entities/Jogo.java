package entities;

import enums.JogoCategoria;

import java.io.Serializable;

public class Jogo implements Serializable, Comparable<Jogo> {
    private static final long serialVersionUID = 1L;
    private String nome;
    private double precoBase;
    private double desconto;
    private JogoCategoria categoria;

    public Jogo(String nome, double precoBase, JogoCategoria categoria) {
        this.nome = nome;
        this.precoBase = precoBase;
        this.categoria = categoria;
    }

    public Jogo() {
    }

    public String getNome() {
        return nome;
    }

    public void descontoValido(double pctDesconto) {
        if (categoria.descontoValido(pctDesconto)) {
            throw new IllegalArgumentException("Desconto inválido");
        }
    }

    public double precoFinal() {
        return this.precoBase - (this.precoBase *  desconto);
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public JogoCategoria getCategoria() {
        return categoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public void setCategoria(JogoCategoria categoria) {
        this.categoria = categoria;
    }

    public void setPctDesconto(double pctDesconto) {
        if (categoria.descontoValido(pctDesconto)) {
            this.desconto = pctDesconto;
        } else {
            throw new IllegalArgumentException("Desconto inválido");
        }
    } 


    @Override
    public int compareTo(Jogo o) {
        return this.nome.compareTo(o.nome);
    }

    @Override
    public String toString() {
        return "Jogo: " + this.nome + " - " + this.precoBase + " - " + this.categoria;
    }
}
