package model;

public class VagaRegularModel extends VagaModel {

    public VagaRegularModel(String id) {
        super(id);
    }

    @Override
    public double calcularPreco(int minutos) {
        return Math.min((minutos / 15) * precoPorFacao, limite);
    }
    
    @Override
    public String getTipo() {
        return "Regular";
    }
}
