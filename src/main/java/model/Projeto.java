package model;

import java.util.ArrayList;
import java.util.List;

public class Projeto {
    public String codigo;
    public String nome;
    public double custo;
    public int beneficio;
    public boolean obrigatorio;
    public List<String> incompativeis;

    public Projeto(String codigo, String nome, double custo, int beneficio, boolean obrigatorio) {
        this.codigo = codigo;
        this.nome = nome;
        this.custo = custo;
        this.beneficio = beneficio;
        this.obrigatorio = obrigatorio;
        this.incompativeis = new ArrayList<>();
    }

    public double razaoRetornoPorCusto() {
        return (double) beneficio / custo;
    }

    public static void tornarIncompativeis(Projeto a, Projeto b) {
        a.incompativeis.add(b.codigo);
        b.incompativeis.add(a.codigo);
    }

}
