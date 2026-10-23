package service;

import model.No;
import model.Projeto;

import java.util.*;

public class Selecao {
    private static double calcularLimiteSuperior(No no, Projeto[] projetos, double orcamento) {
        if (no.custoAtual > orcamento) {
            return 0;
        }

        double limite = no.beneficioAtual;
        double custoTotal = no.custoAtual;
        int j = no.nivel + 1;

        while (j < projetos.length && custoTotal + projetos[j].custo <= orcamento) {
            custoTotal += projetos[j].custo;
            limite += projetos[j].beneficio;
            j++;
        }

        if (j < projetos.length) {
            double razao = (double) projetos[j].beneficio / projetos[j].custo;
            limite += (orcamento - custoTotal) * razao;
        }

        return limite;
    }

    public static No resolver(Projeto[] projetos, double orcamento) {
        List<Projeto> opcionais = new ArrayList<>();
        List<String> codigosObrigatorios = new ArrayList<>();
        double custoObrigatorios = 0;
        int beneficioObrigatorios = 0;

        for (Projeto projeto : projetos) {
            if (projeto.obrigatorio) {
                codigosObrigatorios.add(projeto.codigo);
                custoObrigatorios += projeto.custo;
                beneficioObrigatorios += projeto.beneficio;
            } else {
                opcionais.add(projeto);
            }
        }

        if (custoObrigatorios > orcamento) {
            throw new IllegalArgumentException(
                    "Os projetos obrigatórios (R$ " + custoObrigatorios
                            + ") excedem o orçamento (R$ " + orcamento + ")");
        }

        Projeto[] projetosOrdenados = opcionais.toArray(new Projeto[0]);
        Arrays.sort(projetosOrdenados,
                Comparator.comparingDouble(Projeto::razaoRetornoPorCusto).reversed());

        PriorityQueue<No> filaDePrioridade = new PriorityQueue<>(
                Comparator.comparingDouble((No n) -> n.limiteSuperior).reversed());

        No raiz = new No(-1, beneficioObrigatorios, custoObrigatorios, codigosObrigatorios);
        raiz.limiteSuperior = calcularLimiteSuperior(raiz, projetosOrdenados, orcamento);
        filaDePrioridade.add(raiz);

        No melhorNo = new No(-1, beneficioObrigatorios, custoObrigatorios, codigosObrigatorios);

        while (!filaDePrioridade.isEmpty()) {
            No noAtual = filaDePrioridade.poll();

            if (noAtual.limiteSuperior <= melhorNo.beneficioAtual) break;

            int proximoNivel = noAtual.nivel + 1;
            if (proximoNivel >= projetosOrdenados.length) continue;

            Projeto projetoAtual = projetosOrdenados[proximoNivel];

            if (noAtual.custoAtual + projetoAtual.custo <= orcamento) {
                List<String> adotadosComProjeto = new ArrayList<>(noAtual.projetosAdotados);
                adotadosComProjeto.add(projetoAtual.codigo);

                No noComProjeto = new No(
                        proximoNivel,
                        noAtual.beneficioAtual + projetoAtual.beneficio,
                        noAtual.custoAtual + projetoAtual.custo,
                        adotadosComProjeto);
                noComProjeto.limiteSuperior =
                        calcularLimiteSuperior(noComProjeto, projetosOrdenados, orcamento);

                if (noComProjeto.beneficioAtual > melhorNo.beneficioAtual) {
                    melhorNo = noComProjeto;
                }
                if (noComProjeto.limiteSuperior > melhorNo.beneficioAtual) {
                    filaDePrioridade.add(noComProjeto);
                }
            }

            No noSemProjeto = new No(
                    proximoNivel,
                    noAtual.beneficioAtual,
                    noAtual.custoAtual,
                    noAtual.projetosAdotados);
            noSemProjeto.limiteSuperior =
                    calcularLimiteSuperior(noSemProjeto, projetosOrdenados, orcamento);

            if (noSemProjeto.limiteSuperior > melhorNo.beneficioAtual) {
                filaDePrioridade.add(noSemProjeto);
            }
        }

        return melhorNo;
    }

    private static boolean temConflito(Projeto candidato, List<String> codigosAdotados) {
        for (String codigo : codigosAdotados) {
            if (candidato.incompativeis.contains(codigo)) {
                return true;
            }
        }
        return false;
    }
}