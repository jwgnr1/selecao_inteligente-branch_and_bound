package model;

import java.util.ArrayList;
import java.util.List;

public class No {
    private int nivel;
    private int beneficioAtual;
    private double custoAtual;
    private double limiteSuperior;
    private List<String> projetosAdotados;

    public No(int nivel, int beneficioAtual, double custoAtual, List<String> projetosAdotados) {
        this.nivel = nivel;
        this.beneficioAtual = beneficioAtual;
        this.custoAtual = custoAtual;
        this.limiteSuperior = 0;
        this.projetosAdotados = new ArrayList<>(projetosAdotados);
    }

    public int getNivel() {return nivel;}

    public void setNivel(int nivel) {this.nivel = nivel;}

    public int getBeneficioAtual() {return beneficioAtual;}

    public void setBeneficioAtual(int beneficioAtual) {this.beneficioAtual = beneficioAtual;}

    public double getCustoAtual() {return custoAtual;}

    public void setCustoAtual(double custoAtual) {this.custoAtual = custoAtual;}

    public double getLimiteSuperior() {return limiteSuperior;}

    public void setLimiteSuperior(double limiteSuperior) {this.limiteSuperior = limiteSuperior;}

    public List<String> getProjetosAdotados() {return projetosAdotados;}

    public void setProjetosAdotados(List<String> projetosAdotados) {this.projetosAdotados = projetosAdotados;}
}
