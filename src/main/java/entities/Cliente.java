package entities;

import interfaces.ITipoCliente;

import java.util.List;

public class Cliente {
    private String nome;
    private String senha;
    private String email;
    private List<Compra> compras;
    private ITipoCliente tipoCliente;
}
