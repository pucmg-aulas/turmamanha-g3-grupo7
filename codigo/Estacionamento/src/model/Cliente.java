package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String id;
    private List<Veiculo> veiculos;

    public Cliente(String nome, String id) {
        this.nome = nome;
        this.id = id;
        this.veiculos = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public boolean apagarVeiculo(String placa) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) { // Verifica se a placa coincide
                veiculos.remove(veiculo); // Remove o veículo encontrado
                return true; 
            }
        }
        return false; 
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }
}
