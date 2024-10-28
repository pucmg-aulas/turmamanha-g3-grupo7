package vagas;

import veiculos.Veiculo;

public abstract class Vaga {
    protected String id;
    protected boolean ocupada;
    protected Veiculo veiculo;

    // Atributos estáticos para o preço por fração e o limite
    protected static double precoporfacao = 4.0; // Preço por cada fração de 15 minutos
    protected static double limite = 50.0;       // Limite máximo do preço

    public Vaga(String id) {
        this.id = id;
        this.ocupada = false;
    }

    public String getId() {
        return id;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void ocuparVaga(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.ocupada = true;
    }

    public void liberarVaga() {
        this.veiculo = null;
        this.ocupada = false;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    // Métodos abstratos para serem implementados nas subclasses
    public abstract double calcularPreco(int minutos); 
    public abstract String getTipo();

    // Métodos para acessar os valores estáticos, caso necessário
    public static double getPrecoPorFacao() {
        return precoporfacao;
    }

    public static double getLimite() {
        return limite;
    }
}
