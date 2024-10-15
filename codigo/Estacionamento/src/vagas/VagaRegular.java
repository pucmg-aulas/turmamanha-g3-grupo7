package vagas;

public class VagaRegular extends Vaga {
    public VagaRegular(String id) {
        super(id);
    }

    @Override
    public double calcularPreco(int minutos) {
        // Usa os valores estáticos precoporfacao e limite da classe Vaga
        return Math.min((minutos / 15) * precoporfacao, limite);
    }
    
    @Override
    public String getTipo() {
        return "Regular";
    }
}
