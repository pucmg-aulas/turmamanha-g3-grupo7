package vagas;

public class VagaIdoso extends Vaga {

    protected static double desconto = 0.87;

    public VagaIdoso(String id) {
        super(id);
    }

    @Override
    public double calcularPreco(int minutos) {
        // Usa os valores estáticos precoporfacao e limite da classe Vaga
        return Math.min((minutos / 15) * precoporfacao * desconto, limite);
    }

    @Override
    public String getTipo() {
        return "Idoso"; 
    }
}
