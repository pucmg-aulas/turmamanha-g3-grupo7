package vagas;

public class VagaPCD extends Vaga {
    public VagaPCD(String id) {
        super(id);
    }
    @Override
    public String getTipo() {
        return "PCD"; 
    }

    @Override
    public double calcularPreco(int minutos) {
        return Math.min((minutos / 15) * 4.0 * 0.87, 50.0);
    }
}
