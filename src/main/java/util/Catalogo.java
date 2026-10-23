package util;

import model.Projeto;

import java.util.LinkedHashMap;
import java.util.Map;

public class Catalogo {
    private final Map<String, Projeto> projetos = new LinkedHashMap<>();

    public void adicionar(Projeto projeto) {

        Validator.validarObjeto(projeto, "Projeto");
        if (projetos.putIfAbsent(projeto.getCodigo(), projeto) != null) {
            throw new IllegalArgumentException("Código duplicado: " + projeto.getCodigo());
        }
    }

    public Projeto buscar(String codigo) {
       Validator.validarString(codigo, "Codigo do projeto");

        Projeto projeto = projetos.get(codigo);
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto não encontrado: " + codigo);
        }

        return projeto;
    }

    public void tornarIncompativeis(String codigoA, String codigoB) {
        Projeto.tornarIncompativeis(buscar(codigoA), buscar(codigoB));
    }

    public void removerIncompativeis(String codigoA, String codigoB) {
        Projeto.removerIncompatibilidade(buscar(codigoA), buscar(codigoB));
    }

    public void remover(String codigo) {
        Validator.validarString(codigo, "Código do projeto");
        if (projetos.remove(codigo) == null) {
            throw new IllegalArgumentException("Projeto não encontrado: " + codigo);
        }
        for (Projeto p : projetos.values()) {
            p.getIncompativeis().remove(codigo);
        }
    }

    public Map<String, Projeto> getProjetosMap() {
        return projetos;
    }

    public Projeto[] comoArray() {
        return projetos.values().toArray(new Projeto[0]);
    }
}
