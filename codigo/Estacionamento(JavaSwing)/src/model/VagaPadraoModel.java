<<<<<<< HEAD
package model;

public class VagaPadraoModel extends VagaModel {

    public VagaPadraoModel(String id) {
        super(id);
    }

    @Override
    public double calcularPreco(int minutos) {
        return Math.min(getPrecoPorFacao() * (minutos / 15), getLimite());
    }

    @Override
    public String getTipo() {
        return "Padrão";
    }
}
=======
package model;

public class VagaPadraoModel extends VagaModel {

    public VagaPadraoModel(String id) {
        super(id);
    }

    @Override
    public double calcularPreco(int minutos) {
        return Math.min(getPrecoPorFacao() * (minutos / 15), getLimite());
    }

    @Override
    public String getTipo() {
        return "Padrão";
    }
}
>>>>>>> 7fd6d79c017107357f86c21e3b81e7610c0461ec
