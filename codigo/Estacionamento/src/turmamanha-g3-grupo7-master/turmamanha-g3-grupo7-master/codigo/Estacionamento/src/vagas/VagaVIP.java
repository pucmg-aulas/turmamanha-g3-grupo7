package vagas;

public class VagaVIP extends Vaga {

    protected static double taxa = 0.87;

    public VagaVIP(String id) {
        super(id);
    }
    
    @Override
    public String getTipo() {
        return "VIP";
    }


    @Override
    public double calcularPreco(int minutos) {
        // Usa os valores estáticos precoporfacao e limite da classe Vaga
        return Math.min((minutos / 15) * precoporfacao * taxa, limite);
    }
}
