
package service;

import util.Validator;
import model.No;
import model.Projeto;

import java.util.*;

public class Selecao {

    public static No resolver(Projeto[] projetos, double orcamento) {
        Validator.validarLista(projetos);
        Validator.validarOrcamento(orcamento);
        List<Projeto> opcionais = new ArrayList<>();
        List<String> codigosObrigatorios = new ArrayList<>();

        double custoObrigatorios = 0;
        int beneficioObrigatorios = 0;

        for (Projeto projeto : projetos) {
            if (projeto.isObrigatorio()) {

                if (temConflito(projeto, codigosObrigatorios)) {
                    throw new IllegalArgumentException("O projeto obrigatório "
                            + projeto.getCodigo() + " é incompatível com outro obrigatório");
                }

                codigosObrigatorios.add(projeto.getCodigo());
                custoObrigatorios += projeto.getCusto();
                beneficioObrigatorios += projeto.getBeneficio();

            } else {
                opcionais.add(projeto);
            }
        }

        if (custoObrigatorios > orcamento) {
            throw new IllegalArgumentException(
                    "Os projetos obrigatórios excedem o orçamento."
            );
        }

        Projeto[] projetosOrdenados = opcionais.toArray(new Projeto[0]);

        Arrays.sort(projetosOrdenados,
                Comparator.comparingDouble(Projeto::razaoRetornoPorCusto).reversed());

        No raiz = new No(-1, beneficioObrigatorios, custoObrigatorios, codigosObrigatorios);

        raiz.setLimiteSuperior(calcularLimiteSuperior(raiz, projetosOrdenados, orcamento));

        return buscarMelhorSolucao(raiz, projetosOrdenados, orcamento);
    }

    private static No buscarMelhorSolucao(No raiz, Projeto[] projetos, double orcamento) {
        PriorityQueue<No> fila = new PriorityQueue<>(Comparator.comparingDouble(No::getLimiteSuperior)
                        .reversed());

        fila.add(raiz);

        No melhorNo = new No(raiz.getNivel(), raiz.getBeneficioAtual(), raiz.getCustoAtual(),
                raiz.getProjetosAdotados());

        while (!fila.isEmpty()) {
            No atual = fila.poll();

            if (atual.getLimiteSuperior()
                    <= melhorNo.getBeneficioAtual()) {
                break;
            }

            melhorNo = ramificar(atual, projetos, orcamento, fila, melhorNo);
        }

        return melhorNo;
    }

    private static No ramificar(No atual, Projeto[] projetos, double orcamento,
            PriorityQueue<No> fila, No melhorNo) {

        int proximoNivel = atual.getNivel() + 1;

        if (proximoNivel >= projetos.length) {
            return melhorNo;
        }

        Projeto projeto = projetos[proximoNivel];

        boolean cabeNoOrcamento =
                atual.getCustoAtual() + projeto.getCusto() <= orcamento;

        boolean semConflito = !temConflito(projeto, atual.getProjetosAdotados());

        if (cabeNoOrcamento && semConflito) {
            List<String> adotados =
                    new ArrayList<>(atual.getProjetosAdotados());

            adotados.add(projeto.getCodigo());

            No comProjeto = new No(proximoNivel, atual.getBeneficioAtual() + projeto.getBeneficio(),
                    atual.getCustoAtual() + projeto.getCusto(),
                    adotados
            );

            comProjeto.setLimiteSuperior(
                    calcularLimiteSuperior(comProjeto, projetos, orcamento));

            if (comProjeto.getBeneficioAtual() > melhorNo.getBeneficioAtual()) {
                melhorNo = comProjeto;
            }

            if (comProjeto.getLimiteSuperior() > melhorNo.getBeneficioAtual()) {
                fila.add(comProjeto);
            }
        }

        No semProjeto = new No(proximoNivel, atual.getBeneficioAtual(),
                atual.getCustoAtual(),
                atual.getProjetosAdotados()
        );

        semProjeto.setLimiteSuperior(calcularLimiteSuperior(semProjeto, projetos, orcamento));

        if (semProjeto.getLimiteSuperior() > melhorNo.getBeneficioAtual()) {
            fila.add(semProjeto);
        }

        return melhorNo;
    }

    private static double calcularLimiteSuperior(No no, Projeto[] projetos, double orcamento) {
        if (no.getCustoAtual() > orcamento) {
            return 0;
        }

        double limite = no.getBeneficioAtual();
        double custoTotal = no.getCustoAtual();
        int j = no.getNivel() + 1;

        while (j < projetos.length
                && custoTotal + projetos[j].getCusto() <= orcamento) {

            custoTotal += projetos[j].getCusto();
            limite += projetos[j].getBeneficio();
            j++;
        }

        if (j < projetos.length) {
            double razao = (double) projetos[j].getBeneficio()
                    / projetos[j].getCusto();

            limite += (orcamento - custoTotal) * razao;
        }

        return limite;
    }

    private static boolean temConflito(
            Projeto candidato, List<String> codigosAdotados) {

        for (String codigo : codigosAdotados) {
            if (candidato.getIncompativeis().contains(codigo)) {
                return true;
            }
        }

        return false;
    }
}
