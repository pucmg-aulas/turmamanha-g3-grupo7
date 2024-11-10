<<<<<<< HEAD
package model;

public abstract class VagaModel {
    protected String id;
    protected boolean ocupada;
    protected VeiculoModel veiculo;

    protected static double precoPorFacao = 4.0;
    protected static double limite = 50.0;

    public VagaModel(String id) {
        this.id = id;
        this.ocupada = false;
    }

    public String getId() {
        return id;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void ocuparVaga(VeiculoModel veiculo) {
        this.veiculo = veiculo;
        this.ocupada = true;
    }

    public void liberarVaga() {
        this.veiculo = null;
        this.ocupada = false;
    }

    public VeiculoModel getVeiculo() {
        return veiculo;
    }

    public abstract double calcularPreco(int minutos);
    public abstract String getTipo();

    public static double getPrecoPorFacao() {
        return precoPorFacao;
    }

    public static double getLimite() {
        return limite;
    }

    // Novo método para definir o estado de ocupação
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
}
=======
package model;

public abstract class VagaModel {
    protected String id;
    protected boolean ocupada;
    protected VeiculoModel veiculo;

    protected static double precoPorFacao = 4.0;
    protected static double limite = 50.0;

    public VagaModel(String id) {
        this.id = id;
        this.ocupada = false;
    }

    public String getId() {
        return id;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void ocuparVaga(VeiculoModel veiculo) {
        this.veiculo = veiculo;
        this.ocupada = true;
    }

    public void liberarVaga() {
        this.veiculo = null;
        this.ocupada = false;
    }

    public VeiculoModel getVeiculo() {
        return veiculo;
    }

    public abstract double calcularPreco(int minutos);
    public abstract String getTipo();

    public static double getPrecoPorFacao() {
        return precoPorFacao;
    }

    public static double getLimite() {
        return limite;
    }

    // Novo método para definir o estado de ocupação
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
}
>>>>>>> 7fd6d79c017107357f86c21e3b81e7610c0461ec
