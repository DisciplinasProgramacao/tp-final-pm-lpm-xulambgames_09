package entities;

import enums.DescontoCompra;

import java.time.LocalDate;
import java.util.List;

public class Compra {
    private Cliente cliente;
    private LocalDate dataCompra;
    private DescontoCompra descontoCompra;
    private List<Jogo> jogos;
}
