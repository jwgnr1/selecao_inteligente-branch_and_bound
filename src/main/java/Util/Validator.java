package Util;

public class Validator {
    public static void validarObjeto(Object objeto, String nome) {
        if (objeto == null) {
            throw new IllegalArgumentException(nome + " não pode ser nulo");
        }
    }

    public static void validarString(String texto, String nome) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(nome + " não pode ser nulo, vazio ou conter somente espaços");
        }
    }

    public static void validarCusto(double custo) {
        if(custo <= 0) {
            throw new IllegalArgumentException("Custo deve ser maior que zero.");
        }
    }

    public static void validarBeneficio(int beneficio) {
        if(beneficio < 0) {
            throw new IllegalArgumentException("Beneficio não pode ser menor que 0.");
        }
    }

    public static void validarProjeto(String codigo, String nome, double custo, int beneficio, boolean obrigatorio) {
        validarString(codigo, "Codigo");
        validarString(nome, "Nome");
        validarCusto(custo);
        validarBeneficio(beneficio);

    }


    public static void validarLista(Object[] lista) {
        if(lista == null) {
            throw new IllegalArgumentException("Lista não pode ser nula.");
        }

        for(Object item : lista) {
            validarObjeto(item, "item da lista" );
        }
    }

    public static void validarOrcamento(double orcamento) {
        if(orcamento <= 0) {
            throw new IllegalArgumentException("Orçamento tem que ser positivo.");
        }
    }

}
