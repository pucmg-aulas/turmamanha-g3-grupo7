package model;

import java.util.ArrayList;
import java.util.List;

public class ClienteModel {
    private String nome;
    private String id;
    private List<VeiculoModel> veiculos; // Lista de VeiculoModel

    public ClienteModel(String nome, String id) {
        this.nome = nome;
        this.id = id;
        this.veiculos = new ArrayList<>(); // Inicializa a lista de veículos
    }

    public void adicionarVeiculo(VeiculoModel veiculo) {
        veiculos.add(veiculo); // Adiciona um objeto VeiculoModel
    }

    public List<VeiculoModel> getVeiculos() {
        return veiculos; // Retorna a lista de VeiculoModel
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cliente: " + nome + " (ID: " + id + ")";
    }
}
