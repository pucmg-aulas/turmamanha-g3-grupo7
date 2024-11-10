package model;

public class VagaPCD extends Vaga {
 
    protected static double desconto = 0.87;

    public VagaPCD(String id) {
        super(id);
    }

    @Override
    public String getTipo() {
        return "PCD";
    }

    @Override
    public double calcularPreco(int minutos) {
     // Usa os valores estáticos precoporfacao e limite da classe Vaga
        return Math.min((minutos / 15) * precoporfacao * desconto, limite);
    }
}
