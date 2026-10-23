package model;

import Util.Validator;

import java.util.ArrayList;
import java.util.List;

public class Projeto {
    private String codigo;
    private String nome;
    private double custo;
    private int beneficio;
    private boolean obrigatorio;
    private List<String> incompativeis;

    public Projeto(String codigo, String nome, double custo, int beneficio, boolean obrigatorio) {
        Validator.validarProjeto(codigo, nome, custo, beneficio, obrigatorio);
        this.codigo = codigo;
        this.nome = nome;
        this.custo = custo;
        this.beneficio = beneficio;
        this.obrigatorio = obrigatorio;
        this.incompativeis = new ArrayList<>();
    }

    public String getCodigo() {return codigo;}

    public String getNome() {return nome;}

    public void setNome(String nome) {
        Validator.validarString(nome, "Nome");
        this.nome = nome;}

    public double getCusto() {return custo;}

    public void setCusto(double custo) {
        Validator.validarCusto(custo);
        this.custo = custo;}

    public int getBeneficio() {return beneficio;}

    public void setBeneficio(int beneficio) {
        Validator.validarBeneficio(beneficio);
        this.beneficio = beneficio;}

    public boolean isObrigatorio() {return obrigatorio;}

    public void setObrigatorio(boolean obrigatorio) {this.obrigatorio = obrigatorio;}

    public List<String> getIncompativeis() {return incompativeis;}

    public void setIncompativeis(List<String> incompativeis) {this.incompativeis = incompativeis;}

    public double razaoRetornoPorCusto() {
        return (double) beneficio / custo;
    }

    public static void tornarIncompativeis(Projeto a, Projeto b) {
        Validator.validarObjeto(a, "Projeto");
        Validator.validarObjeto(b, "Projeto");
        a.incompativeis.add(b.codigo);
        b.incompativeis.add(a.codigo);
    }

}
