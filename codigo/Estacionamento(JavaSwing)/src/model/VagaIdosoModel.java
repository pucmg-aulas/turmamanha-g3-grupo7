package model;

public class VagaIdosoModel extends VagaModel {

    private static final double DESCONTO = 0.87;

    public VagaIdosoModel(String id) {
        super(id);
    }

    @Override
    public double calcularPreco(int minutos) {
       
        return Math.min((minutos / 15) * getPrecoPorFacao() * DESCONTO, getLimite());
    }

    @Override
    public String getTipo() {
        return "Idoso"; 
    }
}
