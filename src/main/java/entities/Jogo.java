package entities;

import enums.JogoCategoria;

import java.io.Serializable;

public class Jogo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private double precoBase;
    private double desconto;
    private JogoCategoria categoria;
}
