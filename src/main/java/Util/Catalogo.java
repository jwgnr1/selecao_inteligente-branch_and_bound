package Util;

import model.Projeto;

import java.util.LinkedHashMap;
import java.util.Map;

public class Catalogo {
    private final Map<String, Projeto> projetos = new LinkedHashMap<>();

    public void adicionar(Projeto projeto) {
        if (projetos.putIfAbsent(projeto.codigo, projeto) != null) {
            throw new IllegalArgumentException("Código duplicado: " + projeto.codigo);
        }
    }

    public Projeto buscar(String codigo) {
        Projeto projeto = projetos.get(codigo);
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto não encontrado: " + codigo);
        }
        return projeto;
    }

    public void tornarIncompativeis(String codigoA, String codigoB) {
        Projeto.tornarIncompativeis(buscar(codigoA), buscar(codigoB));
    }

    public Projeto[] comoArray() {
        return projetos.values().toArray(new Projeto[0]);
    }
}
