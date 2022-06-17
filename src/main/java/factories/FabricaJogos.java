package factories;

import enums.JogoCategoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return categorias.get(categorias.indexOf(JogoCategoria.valueOf(categoria.toUpperCase())));
    }

    public static List<JogoCategoria> getCategorias() {
        return categorias;
    }

    public static JogoCategoria getJogoCategoria(int index) {
        try {
            return categorias.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Categoria não encontrada");
        }
    }
}
