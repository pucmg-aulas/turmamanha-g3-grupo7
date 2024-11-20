package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstacionamentoModel {
    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private List<VagaModel> vagas;
    private List<VeiculoModel> veiculosEstacionados;
    private Map<String, TicketEstacionamentoModel> ticketsAtivos;
    private double precoArrecadado;

    public EstacionamentoModel(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.vagas = new ArrayList<>();
        this.veiculosEstacionados = new ArrayList<>();
        this.ticketsAtivos = new HashMap<>();
    }

    public EstacionamentoModel(int id, String nome, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.vagas = new ArrayList<>();
        this.veiculosEstacionados = new ArrayList<>();
        this.ticketsAtivos = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<VagaModel> getVagas() {
        return vagas;
    }

    public void adicionarVaga(VagaModel vaga) {
        this.vagas.add(vaga);
    }

    public void adicionarVeiculoEstacionado(VeiculoModel veiculo) {
        veiculosEstacionados.add(veiculo);
    }

    public void removerVeiculoEstacionado(VeiculoModel veiculo) {
        veiculosEstacionados.remove(veiculo);
    }

    public List<VeiculoModel> getVeiculosEstacionados() {
        return veiculosEstacionados;
    }

    // Métodos para adicionar e remover tickets ativos
    public void adicionarTicketAtivo(String idVaga, TicketEstacionamentoModel ticket) {
        ticketsAtivos.put(idVaga, ticket);
    }

    public TicketEstacionamentoModel removerTicketAtivo(String idVaga) {
        return ticketsAtivos.remove(idVaga);
    }

    public Map<String, TicketEstacionamentoModel> getTicketsAtivos() {
        return ticketsAtivos;
    }

    public double getPrecoArrecadado() {
        return precoArrecadado;
    }

    public void incrementarPrecoArrecadado(double valor) {
        this.precoArrecadado += valor;
    }
}