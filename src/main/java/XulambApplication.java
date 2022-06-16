import entities.Cliente;
import entities.Empolgado;
import factories.FabricaJogos;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class XulambApplication {

    static final String PATH_TUDO = "src/main/resources/tudo.bin";
    static final String LOCAL_DB = "./db";
    static final String arqClientes = LOCAL_DB + "/Clientes.txt";
    static Scanner teclado;
    static TreeSet<Cliente> conjuntoClientes;

    public static void main(String[] args) throws IOException {
        int opcao = 0;
        teclado = new Scanner(System.in, StandardCharsets.UTF_8);
        conjuntoClientes = (TreeSet<Cliente>) carregarDados();
        Cliente.setProximoCodigo(conjuntoClientes.stream() // próximo código disponível para criar cliente
                .mapToInt(Cliente::getId)
                .max()
                .orElse(1));

        Cliente cliente = new Cliente("Hugo", "senha123", "hugo@gmail.com", new Empolgado());
        conjuntoClientes.add(cliente);

        gravarDados();
    }

    /**
     * Gravação serializada do conjunto de clientes
     *
     * @throws IOException Em caso de erro na escrita ou abertura do arquivo
     *                     (propagação de exceção)
     */
    public static void gravarDados() throws IOException {
        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(PATH_TUDO));
        for (Cliente cliente : conjuntoClientes) {
            obj.writeObject(cliente);
        }
        obj.close();
    }

    /**
     * Carrega dados do arquivo de clientes serialiado. Tratamento de diversas
     * exceções
     *
     */
    public static Set<Cliente>  carregarDados() {
        FileInputStream dados;
        TreeSet<Cliente> todos = new TreeSet<>();

        try {
            dados = new FileInputStream(PATH_TUDO);
            ObjectInputStream obj = new ObjectInputStream(dados);
            while (dados.available() != 0) {
                Cliente novo = (Cliente) obj.readObject();
                todos.add(novo);
            }
            obj.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
            System.out.println("Clientes e pedidos em branco.");
            System.out.print("Nome do arquivo de dados: ");
        } catch (IOException ex) {
            System.out.println("Problema no uso do arquivo.");
            System.out.println("Favor reiniciar o sistema.");
        } catch (ClassNotFoundException cex) {
            System.out.println("Classe não encontrada: avise ao suporte.");
            System.out.println("Clientes e pedidos em branco.");
            todos = new TreeSet<>();
        }

        return todos;
    }
}
