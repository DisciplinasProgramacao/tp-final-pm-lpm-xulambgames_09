package entities;

import enums.DescontoCompra;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private LocalDate dataCompra;
    private DescontoCompra descontoCompra;
    private List<Jogo> jogos;
}
