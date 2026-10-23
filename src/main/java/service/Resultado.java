package service;

import model.Projeto;

import java.util.List;

class Resultado {
    private final long retornoTotal;
    private final long custoTotal;
    private final List<Projeto> projetosEscolhidos;

    public Resultado(long retornoTotal, long custoTotal, List<Projeto> projetosEscolhidos) {
        this.retornoTotal = retornoTotal;
        this.custoTotal = custoTotal;
        this.projetosEscolhidos = projetosEscolhidos;
    }

    public long getRetornoTotal() { return retornoTotal; }
    public long getCustoTotal() { return custoTotal; }
    public List<Projeto> getProjetosEscolhidos() { return projetosEscolhidos; }
}