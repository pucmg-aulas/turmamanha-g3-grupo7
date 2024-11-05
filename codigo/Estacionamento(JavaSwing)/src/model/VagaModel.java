<<<<<<< HEAD
package model;

public abstract class VagaModel {
    protected String id;
    protected boolean ocupada;
    protected VeiculoModel veiculo;

    // Atributos estáticos para o preço por fração e o limite
    protected static double precoPorFacao = 4.0; // Preço por cada fração de 15 minutos
    protected static double limite = 50.0;       // Limite máximo do preço

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
}
=======
package model;

public abstract class VagaModel {
    protected String id;
    protected boolean ocupada;
    protected VeiculoModel veiculo;

    // Atributos estáticos para o preço por fração e o limite
    protected static double precoPorFacao = 4.0; // Preço por cada fração de 15 minutos
    protected static double limite = 50.0;       // Limite máximo do preço

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
}
>>>>>>> d9070482504422a0b550f2cc893f54b1a437c91e
