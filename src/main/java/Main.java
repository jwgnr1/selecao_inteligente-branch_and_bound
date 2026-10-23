import model.No;
import model.Projeto;
import service.Selecao;

public class Main {
    public static void main(String[] args) {
        Projeto[] projetos = {
                new Projeto("P1", "Sistema ERP Interno", 40000, 70, false),
                new Projeto("P2", "App Mobile", 25000, 40, false),
                new Projeto("P3", "Migração para Nuvem", 60000, 90, true),
                new Projeto("P4", "Chatbot IA", 30000, 35, false),
                new Projeto("P5", "Reformulação de Segurança", 50000, 80, false),
                new Projeto("P6", "Ferramenta de BI", 15000, 25, false),
                new Projeto("P7", "Automação RPA", 35000, 50, false),
                new Projeto("P8", "Portal do Cliente", 20000, 30, false)
        };


        double orcamento = 150000;
        No melhor = Selecao.resolver(projetos, orcamento);

        System.out.println("Orçamento: R$ " + orcamento);
        System.out.println("Benefício máximo: " + melhor.beneficioAtual);
        System.out.println("Custo total: R$ " + melhor.custoAtual);
        System.out.println("Projetos adotados: " + melhor.projetosAdotados);
    }
}