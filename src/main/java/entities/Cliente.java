package entities;

import interfaces.ITipoCliente;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import factories.FabricaCategoriaJogos;

public class Cliente implements Comparable<Cliente>, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private static int ultimoCodigo=0;
    private String nome;
    private String senha;
    private String email;
    private List<Compra> compras;
    private ITipoCliente tipoCliente;
    private List<NotaFiscal> extrato;

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
        this.extrato = new LinkedList<>();
    }

    public Cliente(String nome, String senha, String email) {
        this.id = ++ultimoCodigo;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.tipoCliente = null;
        this.compras = new LinkedList<>();
        this.extrato = new LinkedList<>();
    }

    public Optional<List<NotaFiscal>> getExtrato() {
        return Optional.ofNullable(this.extrato.isEmpty() ? null : this.extrato);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setTipoCliente(ITipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public List<Compra> getCompras(){
        return this.compras;
    }

    public Optional<ITipoCliente> getTipoCliente() {
        return Optional.ofNullable(tipoCliente);
    }

    public void incluirCompra(Compra compra){
        this.compras.add(compra);
    }

    public Double totalValorPagoCompras() {
        return this.compras.stream().mapToDouble(Compra::valorAPagar).sum();
    }

    public void comprasPelaCategoriaJogo(String categoria){

        this.compras.stream()
                .flatMap(e -> e.getJogos().stream())
                .filter(i -> i.getCategoria().equals(FabricaCategoriaJogos.getJogoCategoria(categoria)))
                .forEach(i -> System.out.println(i));
    }

    public void extrato() {
        this.getExtrato().ifPresentOrElse(
                e -> {
                    for (NotaFiscal nf : e)
                        System.out.println("============\n" + nf + "\n============");
                },
                () -> System.out.println(this.nome + " não possui nenhum pagamento.")
        );

    }

    @Override
    public String toString() {
        return "Nome: " + this.nome + " - Tipo: " + (this.tipoCliente != null ? this.tipoCliente.toString() : "Cadastrado") + " - e-mail: " + this.email;
    }

    @Override
    public int compareTo(Cliente o) {
        return this.nome.compareTo(o.nome);
    }

    public void incluirPagamento(NotaFiscal notaFiscal) {
        this.extrato.add(notaFiscal);
    }

    public void comprasPelaData(int dia, int mes, int ano){  
        LocalDate ld = LocalDate.of(ano, mes, dia);
        this.compras.stream()
        .filter(i -> i.getData().isEqual(ld))
        .forEach(System.out::println);
    }
}
