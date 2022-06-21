package dao;

import entities.Cliente;
import entities.Jogo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

public class JogoDAO {
    

    public static Cliente getReferenceByName(String nome, TreeSet<Cliente> clientes) {

       return clientes.stream().filter(c -> c.getNome().equals(nome)).findFirst().orElse(null);
    }

    public static void listarJogos(TreeSet<Jogo> jogos) {
        for (Jogo c : jogos) {
            System.out.println(c.getNome());
        }
    }



}
