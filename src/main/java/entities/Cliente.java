package entities;

import interfaces.ITipoCliente;

import java.io.Serializable;
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
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Cliente o) {
        return this.nome.compareTo(o.nome);
    }
}
