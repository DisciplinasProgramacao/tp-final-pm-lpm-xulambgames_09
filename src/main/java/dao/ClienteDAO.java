package dao;

import entities.Cliente;

import java.io.*;
import java.util.TreeSet;

public class ClienteDAO {

    public static Cliente getReferenceByName(String nome, TreeSet<Cliente> clientes) {

       return clientes.stream().filter(c -> c.getNome().equals(nome)).findFirst().orElse(null);
    }
}
