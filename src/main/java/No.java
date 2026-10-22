import java.util.ArrayList;
import java.util.List;

public class No {
    public int nivel;
    public int beneficioAtual;
    public double custoAtual;
    public int limiteSuperior;
    List<String> projetosAdotados;

    public No(int nivel, int beneficioAtual, double custoAtual, List<String> projetosAdotados) {
        this.nivel = nivel;
        this.beneficioAtual = beneficioAtual;
        this.custoAtual = custoAtual;
        this.limiteSuperior = 0;
        this.projetosAdotados = new ArrayList<>(projetosAdotados);
    }
}
