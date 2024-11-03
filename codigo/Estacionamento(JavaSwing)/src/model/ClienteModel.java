package model;

import java.util.ArrayList;
import java.util.List;

public class ClienteModel {
    private String nome;
    private String id;
    private List<VeiculoModel> veiculos;

    public ClienteModel(String nome, String id) {
        this.nome = nome;
        this.id = id;
        this.veiculos = new ArrayList<>();
    }

    public void adicionarVeiculo(VeiculoModel veiculo) {
        veiculos.add(veiculo);
    }

    public List<VeiculoModel> getVeiculos() {
        return veiculos;
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
}
