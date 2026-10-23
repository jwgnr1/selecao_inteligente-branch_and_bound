package view;

import util.Catalogo;
import model.No;
import model.Projeto;
import service.Selecao;

import java.util.Scanner;

public class ConsoleUI {
    private final Catalogo catalogo;
    private double orcamentoPadrao = 50000.0;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public void iniciar() {
        int opcao = -1;
        do {
            System.out.println("\n========================================");
            System.out.println("  SISTEMA DE SELEÇÃO DE PROJETOS (B&B)  ");
            System.out.println("========================================");
            System.out.println("1. Listar Projetos");
            System.out.println("2. Adicionar Projeto");
            System.out.println("3. Remover Projeto");
            System.out.println("4. Mudar Status Obrigatório (Alternar)");
            System.out.println("5. Adicionar Incompatibilidade");
            System.out.println("6. Remover Incompatibilidade");
            System.out.println("7. Alterar Orçamento (Atual: R$ " + String.format("%.2f", orcamentoPadrao) + ")");
            System.out.println("8. Executar Seleção (Branch and Bound)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                String input = scanner.nextLine();
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    listarProjetos();
                    break;
                case 2:
                    adicionarProjeto();
                    break;
                case 3:
                    removerProjeto();
                    break;
                case 4:
                    mudarObrigatorio();
                    break;
                case 5:
                    adicionarIncompatibilidade();
                    break;
                case 6:
                    removerIncompatibilidade();
                    break;
                case 7:
                    alterarOrcamento();
                    break;
                case 8:
                    executarSelecao();
                    break;
                case 0:
                    System.out.println("Saindo do sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção desconhecida.");
            }
        } while (opcao != 0);
    }

    private void listarProjetos() {
        System.out.println("\n--- LISTA DE PROJETOS CADASTRADOS ---");
        if (catalogo.getProjetosMap().isEmpty()) {
            System.out.println("(Nenhum projeto cadastrado)");
            return;
        }
        for (Projeto p : catalogo.getProjetosMap().values()) {
            System.out.printf("[%s] %s\n", p.getCodigo(), p.getNome());
            System.out.printf("    Custo: R$ %.2f | Benefício: %d | Obrigatório: %s\n",
                    p.getCusto(), p.getBeneficio(), p.isObrigatorio() ? "Sim" : "Não");
            if (!p.getIncompativeis().isEmpty()) {
                System.out.printf("    Incompatíveis com: %s\n", String.join(", ", p.getIncompativeis()));
            }
            System.out.println("----------------------------------------");
        }
    }

    private void adicionarProjeto() {
        try {
            System.out.println("\n--- ADICIONAR NOVO PROJETO ---");
            System.out.print("Código do projeto (ex: P9): ");
            String codigo = scanner.nextLine().trim();

            System.out.print("Nome do projeto: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Custo (R$): ");
            double custo = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Benefício (pontos): ");
            int beneficio = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("É obrigatório? (s/n): ");
            String respObrig = scanner.nextLine().trim().toLowerCase();
            boolean obrigatorio = respObrig.startsWith("s");

            Projeto p = new Projeto(codigo, nome, custo, beneficio, obrigatorio);
            catalogo.adicionar(p);
            System.out.println("Projeto adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar projeto: " + e.getMessage());
        }
    }

    private void removerProjeto() {
        try {
            System.out.println("\n--- REMOVER PROJETO ---");
            System.out.print("Digite o código do projeto a ser removido: ");
            String codigo = scanner.nextLine().trim();
            catalogo.remover(codigo);
            System.out.println("Projeto removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao remover projeto: " + e.getMessage());
        }
    }

    private void mudarObrigatorio() {
        try {
            System.out.println("\n--- ALTERAR STATUS OBRIGATÓRIO ---");
            System.out.print("Digite o código do projeto: ");
            String codigo = scanner.nextLine().trim();
            Projeto p = catalogo.buscar(codigo);
            boolean novoStatus = !p.isObrigatorio();
            p.setObrigatorio(novoStatus);
            System.out.printf("Status do projeto [%s] alterado para: Obrigatório = %s\n",
                    codigo, novoStatus ? "Sim" : "Não");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void adicionarIncompatibilidade() {
        try {
            System.out.println("\n--- ADICIONAR INCOMPATIBILIDADE ---");
            System.out.print("Código do primeiro projeto: ");
            String c1 = scanner.nextLine().trim();
            System.out.print("Código do segundo projeto: ");
            String c2 = scanner.nextLine().trim();

            catalogo.tornarIncompativeis(c1, c2);
            System.out.printf("Projetos %s e %s agora são incompatíveis!\n", c1, c2);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerIncompatibilidade() {
        try {
            System.out.println("\n--- REMOVER INCOMPATIBILIDADE ---");
            System.out.print("Código do primeiro projeto: ");
            String c1 = scanner.nextLine().trim();
            System.out.print("Código do segundo projeto: ");
            String c2 = scanner.nextLine().trim();

            catalogo.removerIncompativeis(c1, c2);
            System.out.printf("Incompatibilidade entre %s e %s removida!\n", c1, c2);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void alterarOrcamento() {
        try {
            System.out.println("\n--- ALTERAR ORÇAMENTO ---");
            System.out.printf("Orçamento atual: R$ %.2f\n", orcamentoPadrao);
            System.out.print("Digite o novo orçamento (R$): ");
            double novoOrcamento = Double.parseDouble(scanner.nextLine().trim());
            if (novoOrcamento <= 0) {
                throw new IllegalArgumentException("O orçamento deve ser maior que zero.");
            }
            orcamentoPadrao = novoOrcamento;
            System.out.printf("Orçamento alterado com sucesso para R$ %.2f\n", orcamentoPadrao);
        } catch (Exception e) {
            System.out.println("Erro ao alterar orçamento: " + e.getMessage());
        }
    }

    private void executarSelecao() {
        try {
            System.out.println("\n--- EXECUTAR SELEÇÃO (BRANCH AND BOUND) ---");
            System.out.printf("Utilizando orçamento atual: R$ %.2f\n", orcamentoPadrao);

            No melhor = Selecao.resolver(catalogo.comoArray(), orcamentoPadrao);

            System.out.println("\n========================================");
            System.out.println("          RESULTADO DA SELEÇÃO          ");
            System.out.println("========================================");
            System.out.printf("Orçamento limite: R$ %.2f\n", orcamentoPadrao);
            System.out.printf("Benefício máximo: %d\n", melhor.getBeneficioAtual());
            System.out.printf("Custo total: R$ %.2f\n", melhor.getCustoAtual());
            System.out.println("Projetos adotados:");
            if (melhor.getProjetosAdotados().isEmpty()) {
                System.out.println("  (Nenhum projeto selecionado)");
            } else {
                for (String codigo : melhor.getProjetosAdotados()) {
                    try {
                        Projeto p = catalogo.buscar(codigo);
                        System.out.printf("  - [%s] %s | Custo: R$ %.2f | Benefício: %d\n",
                                p.getCodigo(), p.getNome(), p.getCusto(), p.getBeneficio());
                    } catch (Exception ignored) {
                        System.out.printf("  - [%s]\n", codigo);
                    }
                }
            }
            System.out.println("========================================");
        } catch (Exception e) {
            System.out.println("Erro na seleção: " + e.getMessage());
        }
    }
}
