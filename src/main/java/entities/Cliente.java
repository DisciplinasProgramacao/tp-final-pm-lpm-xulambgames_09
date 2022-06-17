package entities;

import interfaces.ITipoCliente;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Cliente implements Comparable<Cliente>, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private static int ultimoCodigo=0;
    private String nome;
    private String senha;
    private String email;
    private List<Compra> compras;
    private ITipoCliente tipoCliente;

    public static void setProximoCodigo(int cod){
        if(ultimoCodigo<=cod)
            ultimoCodigo = cod+1;
    }

    public Cliente(String nome, String senha, String email, ITipoCliente tipoCliente) {
        this.id = ++ultimoCodigo;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.tipoCliente = tipoCliente;
        this.compras = new LinkedList<>();
    }

    public Cliente(String nome, String senha, String email) {
        this.id = ++ultimoCodigo;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.tipoCliente = null;
        this.compras = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void addCompra(Compra compra){
        this.compras.add(compra);
    }

    public List<Compra> getCompras(){
        return this.compras;
    }

    public ITipoCliente getTipoCliente() {
        return tipoCliente;
    }

    @Override
    public int compareTo(Cliente o) {
        return this.nome.compareTo(o.nome);
    }

    public Double totalEmCompras() {
        return 0.0;
    }

    public String toString() {
        return "Cliente: " + this.nome + " - " + this.email;
    }
}
