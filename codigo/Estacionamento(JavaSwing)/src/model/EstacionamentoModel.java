package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstacionamentoModel {
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
        this.precoArrecadado = 0.0;
    }

    public void adicionarVaga(VagaModel vaga) {
        if (!vagas.contains(vaga)) {
            vagas.add(vaga);
        } 
    }
    
    public void limparVagas() {
        for (VagaModel vaga : vagas) {
            if (vaga.isOcupada()) {
                vaga.liberarVaga(); 
            }
        }
    }

    public void adicionarVeiculoEstacionado(VeiculoModel veiculo) {
        veiculosEstacionados.add(veiculo);
    }

    public void removerVeiculoEstacionado(VeiculoModel veiculo) {
        veiculosEstacionados.remove(veiculo);
    }

    public void adicionarTicketAtivo(String idVaga, TicketEstacionamentoModel ticket) {
        ticketsAtivos.put(idVaga, ticket);
    }

    public TicketEstacionamentoModel removerTicketAtivo(String idVaga) {
        return ticketsAtivos.remove(idVaga);
    }

    public List<VagaModel> getVagas() {
        return vagas;
    }

    public List<VeiculoModel> getVeiculosEstacionados() {
        return veiculosEstacionados;
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

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }
}
