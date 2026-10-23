import util.Catalogo;
import view.ConsoleUI;

public class Main {
    public static void main(String[] args) {
       Catalogo catalogo = new Catalogo();

//        catalogo.adicionar(new Projeto ("P1", "Monetização do Laboratórios", 18000, 90, false));
//        catalogo.adicionar(new Projeto ("P2", "Plataforma de Ensino", 12000, 65, false));
//        catalogo.adicionar(new Projeto ("P3", "Rede sem fio institucional", 10000, 55, false));
//        catalogo.adicionar(new Projeto ("P4", "Servidores de Pesquisa", 22000, 110, false));
//        catalogo.adicionar(new Projeto ("P5", "Sistema de Segurança", 8000, 40, false));
//        catalogo.adicionar(new Projeto ("P6", "Capacitação Docente", 6000, 35, false));
//        catalogo.adicionar(new Projeto ("P7", "Laboratório de IA", 20000, 105, false));
//        catalogo.adicionar(new Projeto ("P8", "Sistema de Gestão Acadêmica", 14000, 75, false));
//
//        catalogo.tornarIncompativeis("P4", "P7");

        ConsoleUI ui = new ConsoleUI(catalogo);
        ui.iniciar();
    }
}
