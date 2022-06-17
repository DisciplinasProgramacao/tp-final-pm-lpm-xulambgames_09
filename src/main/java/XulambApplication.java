import dao.ClienteDAO;
import dao.JogoDAO;
import entities.Cliente;
import entities.Compra;
import entities.Jogo;
import enums.JogoCategoria;
import factories.FabricaJogos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class XulambApplication {

    static final String PATH_RESOURCES = "src/main/resources";
    static final String arqClientes = PATH_RESOURCES + "/clientes.bin";
    static final String arqJogos = PATH_RESOURCES + "/jogos.bin";
    static final String arqCompras = PATH_RESOURCES + "/compras.bin";
    static TreeSet<Cliente> conjuntoClientes;
    static TreeSet<Compra> conjuntoCompras;
    static TreeSet<Jogo> conjuntoJogos;
    static Scanner teclado;

    static Cliente clienteAtual = null;
    static Compra compraAtual = null;
    static Jogo itemAtual = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int opcao = 0;
        teclado = new Scanner(System.in, StandardCharsets.UTF_8);
        conjuntoClientes = (TreeSet<Cliente>) carregarDados(arqClientes, Cliente.class);
        conjuntoCompras = (TreeSet<Compra>) carregarDados(arqCompras, Compra.class);
        conjuntoJogos = (TreeSet<Jogo>) carregarDados(arqJogos, Jogo.class);
        Cliente.setProximoCodigo(conjuntoClientes.stream()
                .mapToInt(Cliente::getId)
                .max()
                .orElse(1));

        do {
            opcao = menu(opcao);
            switch (opcao) {
                case 11:
                    if (clienteAtual == null) {
                        clienteAtual = localizarCliente();
                    }
                    if (compraAtual == null) {
                        compraAtual = criarPedido();
                    } else
                        System.out.println("Já há pedido em aberto.");
                    break;

                case 12:
                    if (clienteAtual == null) {
                        System.out.println("Não há cliente ativo.");
                    } else if (compraAtual == null) {
                        System.out.println("Não há pedido em aberto");
                    } else {
                        adicionarJogoACompra();
                    }

                    break;

                case 13:
                    try {

                        System.out.println("Cliente: " + clienteAtual.getNome());
                        System.out.println(compraAtual.relatorio());
                    } catch (NullPointerException nex) {
                        System.out.println("Não há cliente ativo ou pedido em aberto.");
                    }
                    break;

                case 14:
                    if (compraAtual != null) {
                        try {

                            System.out.println("PEDIDO FECHADO.");
                            clienteAtual.addCompra(compraAtual);
                            conjuntoCompras.add(compraAtual);
                            System.out.println(compraAtual.relatorio());
                            compraAtual = null;
                        } catch (NullPointerException nex) {
                            System.out.println("Não há cliente ativo");
                        }
                    } else {
                        System.out.println("Não há pedido em aberto.");
                    }
                    break;

                case 21:

                    try {
                        clienteAtual = localizarCliente();
                        System.out.println(clienteAtual);
                        System.out.println("ÚLTIMO PEDIDO: " + clienteAtual.getCompras().get(clienteAtual.getCompras().size() - 1));
                    } catch (NullPointerException ne) {
                        System.out.println("Cliente não encontrado.");
                    } catch (NoSuchElementException nse) {
                        System.out.println("Cliente não tem pedidos.");
                    }
                    break;

                case 22:

                    try {
                        clienteAtual = localizarCliente();

                        System.out.println("CLIENTE: " + clienteAtual);
                        System.out.println("TOTAL DE PEDIDOS: " + clienteAtual.getCompras().size());
                        System.out.println("GASTO TOTAL COM PEDIDOS: " +
                                String.format("%.2f", clienteAtual.totalEmCompras()));
                        System.out.println("MÉDIA POR PEDIDO: " +
                                String.format("%.2f,", clienteAtual.getCompras().stream()
                                        .mapToDouble(Compra::getValorPago)
                                        .average()
                                        .getAsDouble()));
                        System.out.println("ÚLTIMO PEDIDO: " + clienteAtual.getCompras().get(clienteAtual.getCompras().size() - 1));
                    } catch (NullPointerException ne) {
                        System.out.println("Cliente não encontrado.");
                    } catch (NoSuchElementException nse) {
                        System.out.println("Cliente não tem pedidos.");
                    }

                    break;
                case 31:
                    conjuntoJogos.add(criarNovoJogo());
                    break;
                case 32:
                    Jogo jogoLocalizado = localizarJogo();
                    if (jogoLocalizado != null) {
                        System.out.println(jogoLocalizado);
                    } else {
                        System.out.println("Jogo não encontrado.");
                    }
                    break;
                case 33:
                    jogoLocalizado = localizarJogo();
                    if (jogoLocalizado != null) {
                        System.out.println(jogoLocalizado);
                        alterarCategoria(jogoLocalizado);
                        System.out.println("CATEGORIA ALTERADA COM SUCESSO.");
                        System.out.println(jogoLocalizado);
                    } else {
                        System.out.println("Jogo não encontrado.");
                    }
                    break;
                case 34:
                    JogoDAO.listarJogos(conjuntoJogos);
                    break;
                case 41:

                    Optional<Compra> maiorDeHoje = conjuntoCompras.stream()
                            .filter(ped -> ped.getData().equals(LocalDate.now()))
                            .max(Compra::compareTo);
                    maiorDeHoje.ifPresentOrElse(
                            (p) -> System.out.println("Maior pedido de hoje:\n" + p),
                            () -> System.out.println("Sem pedidos hoje até agora."));

                    break;

                case 42:

                    System.out.print("Total arrecadado pelo restauraurante: R$ ");
                    System.out.println(String.format("%.2f",
                            conjuntoCompras.stream()
                                    .mapToDouble(Compra::valorTotal)
                                    .sum()));
                    break;
                case 43:

                    System.out.println("Cliente com maior valor total em pedidos.");
                    try {
                        Cliente maiorTotal = conjuntoClientes.stream()
                                .max((c1, c2) -> c1.totalEmCompras() > c2.totalEmCompras() ? 1 : -1)
                                .orElseThrow();

                        System.out
                                .println(maiorTotal + " com R$ " + String.format("%.2f", maiorTotal.totalEmCompras()));
                    } catch (NoSuchElementException e) {
                        System.out.println("Não há clientes cadastrados");
                    }
                    break;
                case 44:
                    System.out.print("Digite o nome do arquivo para saída:");
                    String arquivo = teclado.nextLine();
//                        relatorioVendas(arquivo, todosOsPedidos);

                    break;

                case 45:
                    System.out.println(resumoClientes());
                    break;
            }
        } while (opcao != 0);

        gravarDados(conjuntoClientes, arqClientes);
        gravarDados(conjuntoJogos, arqJogos);
        gravarDados(conjuntoCompras, arqCompras);
    }

    private static void alterarCategoria(Jogo jogo) {
        System.out.println("Digite a nova categoria do jogo:");
        int iCategoria = escolherCategoria();
        jogo.setCategoria(FabricaJogos.getJogoCategoria(iCategoria));
    }

    private static Jogo localizarJogo() {
        System.out.println("Digite o nome do jogo:");
        String nome = teclado.nextLine();
        return conjuntoJogos.stream().filter(j -> j.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }

    private static int escolherCategoria() {
        int iCategoria;
        System.out.println("Escolha a categoria:");
        int i = 1;
        for (JogoCategoria categoria : FabricaJogos.getCategorias()) {
            System.out.println(i + " - " + categoria.name());
            i++;
        }
        iCategoria = Integer.parseInt(teclado.nextLine());
        return iCategoria - 1;
    }

    private static Jogo criarNovoJogo() {
        Jogo novoJovo = new Jogo();
        System.out.println("Digite o nome do jogo:");
        String nomeJogo = teclado.nextLine();
        System.out.println("Digite o preço base:");
        double precoBase = Double.parseDouble(teclado.nextLine());
        int iCategoria = escolherCategoria();
        novoJovo.setCategoria(FabricaJogos.getJogoCategoria(iCategoria));
        novoJovo.setNome(nomeJogo);
        novoJovo.setPrecoBase(precoBase);

        return novoJovo;

    }

    private static String resumoClientes() {
        return null;
    }

    private static void adicionarJogoACompra() {
        itemAtual = pegarJogoExistente();
        compraAtual.addItem(itemAtual);
        System.out.println(itemAtual.getNome() + " adicionado ao pedido.");
    }

    private static Compra criarPedido() {
        if (compraAtual != null) {
            System.out.println("Existe compra em aberto.");
            return compraAtual;
        }
        try {

            itemAtual = pegarJogoExistente();
            System.out.println(itemAtual.getNome() + " adicionado ao pedido.");
            compraAtual = new Compra(clienteAtual, LocalDate.now(), itemAtual);
        } catch (NullPointerException ex) {
            System.out.println("Cliente não encontrado. Cadastrar cliente.");
            clienteAtual = cadastrarCliente();
        }

        return compraAtual;
    }

    private static Jogo pegarJogoExistente() {
        Jogo novoJogo = null;
        try {
            int opcao;
            System.out.println("XULAMBS GAMES");
            System.out.println("==========================");
            System.out.println("INCLUIR JOGO");
            int i = 1;
            List<Jogo> jogos = new ArrayList<>(conjuntoJogos);
            for (Jogo jogo : jogos) {
                System.out.println(i + " - " + jogo.getNome());
                i++;
            }

            System.out.print("Escolha: ");
            opcao = Integer.parseInt(teclado.nextLine());
            novoJogo = jogos.get(opcao - 1);

            if (novoJogo.getCategoria().getPctDescontoMax() == novoJogo.getCategoria().getPctDescontoMin())
                novoJogo.setPctDesconto(novoJogo.getCategoria().getPctDescontoMax());
            else {
                System.out.println("Desconto a aplicar (" + novoJogo.getCategoria() + "): ");
                opcao = Integer.parseInt(teclado.nextLine());
                novoJogo.setPctDesconto(opcao / 100d);
            }

        } catch (InvalidParameterException ipe) {
            System.out.println("Categoria de jogo inválido.");
        }
        return novoJogo;
    }

    private static Cliente localizarCliente() {
        String nome;
        System.out.println("XULAMBS BURGER & PIZZA");
        System.out.println("==========================");
        System.out.println("LOCALIZAR CLIENTE");
        System.out.print("NOME: ");

        nome = teclado.nextLine();

        Optional<Cliente> achou = Optional.ofNullable(ClienteDAO.getReferenceByName(nome, conjuntoClientes));
        AtomicReference<Cliente> clienteEncontrado = new AtomicReference<>();
        achou.ifPresentOrElse(
                clienteEncontrado::set,
                () -> clienteEncontrado.set(cadastrarCliente()));
        return clienteEncontrado.get();
    }

    private static Cliente cadastrarCliente() {
        Cliente novoCliente;

        System.out.println("XULAMBS GAMES");
        System.out.println("==========================");
        System.out.println("NOVO CLIENTE");
        System.out.print("NOME: ");

        String nome = teclado.nextLine();
        System.out.print("SENHA: ");
        String senha = teclado.nextLine();
        System.out.print("E-MAIL: ");
        String email = teclado.nextLine();
        novoCliente = new Cliente(nome, senha, email);
        conjuntoClientes.add(novoCliente);
        return novoCliente;
    }

    private static int menu(int subnivel) {
        int opcao;
        System.out.println("1 - Compras");
        System.out.println("2 - Clientes");
        System.out.println("3 - Jogos");
        System.out.println("4 - Relatórios");
        System.out.println("0 - Finalizar");
        System.out.print("Digite sua opção: ");
        if ((subnivel % 10) == 0) {
            opcao = teclado.nextInt();
            teclado.nextLine();
        } else
            opcao = subnivel / 10;
        try {
            switch (opcao) {
                case 1:
                    return subMenu(PATH_RESOURCES + "/menuConfigs/menuCompras.txt", 10);
                case 2:
                    return subMenu(PATH_RESOURCES + "/menuConfigs/menuCliente.txt", 20);
                case 3:
                    return subMenu(PATH_RESOURCES + "/menuConfigs/menuJogos.txt", 30);
                case 4:
                    return subMenu(PATH_RESOURCES + "/menuConfigs/menuRelatorios.txt", 40);
                default:
                    return 0;
            }
        } catch (InputMismatchException ie) {
            return -1;
        }
    }

    public static int subMenu(String arquivo, int opcao) {
        int opcaoMenu = 0;
        try {
            Scanner arqMenu = new Scanner(new File(arquivo));
            int op = 1;
            while (arqMenu.hasNextLine()) {
                System.out.println(op + " - " + arqMenu.nextLine());
                op++;
            }
            opcaoMenu = teclado.nextInt();
            teclado.nextLine();
        } catch (FileNotFoundException fe) {
            System.out.println("Arquivo " + arquivo + " não encontrado. Entre em contato com o suporte");

        } catch (InputMismatchException ie) {
            return -1;
        }
        return opcao + opcaoMenu;
    }

    /**
     * Gravação serializada do conjunto de clientes
     *
     * @throws IOException Em caso de erro na escrita ou abertura do arquivo
     *                     (propagação de exceção)
     */
    public static<T> void gravarDados(TreeSet<T> conjunto, String path) throws IOException {
        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(path));
        for (T objeto : conjunto) {
            obj.writeObject(objeto);
        }
        obj.close();
    }

    /**
     * Carrega dados do arquivo de clientes serialiado. Tratamento de diversas
     * exceções
     *
     */
    public static<T> Set<T> carregarDados(String nomeArq, Class<T> classe) throws FileNotFoundException, ClassNotFoundException {
        FileInputStream dados;
        TreeSet<T> todos = new TreeSet<>();

        try {
            dados = new FileInputStream(nomeArq);
            ObjectInputStream obj = new ObjectInputStream(dados);
            while (dados.available() != 0) {
                T novo = (T) obj.readObject();
                todos.add(novo);
            }
            obj.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException ex) {
            throw new FileNotFoundException("Erro de leitura");
        } catch (ClassNotFoundException cex) {
            throw new ClassNotFoundException("Classe não encontrada");
        }

        return todos;
    }
}
