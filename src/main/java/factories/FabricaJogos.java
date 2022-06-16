package factories;

import enums.JogoCategoria;

import java.util.ArrayList;
import java.util.List;

public class FabricaJogos {
    static final List<JogoCategoria> categorias = new ArrayList<>(4);

    static {
        categorias.add(JogoCategoria.LANCAMENTO);
        categorias.add(JogoCategoria.PREMIUM);
        categorias.add(JogoCategoria.REGULAR);
        categorias.add(JogoCategoria.PROMOCAO);
    }

    public static JogoCategoria getJogoCategoria(JogoCategoria categoria) {
        return categorias.get(categorias.indexOf(categoria));
    }

    public static JogoCategoria getJogoCategoria(String categoria) {
        return categorias.get(categorias.indexOf(JogoCategoria.valueOf(categoria)));
    }
}
