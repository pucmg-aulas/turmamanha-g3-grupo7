package estacionamento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tickets.TicketEstacionamento;
import vagas.Vaga;
import vagas.VagaFactory;
import veiculos.Cliente;
import veiculos.Veiculo;


public class Estacionamento {
    private String nome;
    private String endereco;
    private String telefone;
    private List<Vaga> vagas;   
    private List<Veiculo> veiculosEstacionados;
    private Map<String, TicketEstacionamento> ticketsAtivos;
    private double precoArrecadado;


    public Estacionamento(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.vagas = new ArrayList<>();
        this.veiculosEstacionados = new ArrayList<>();
        this.ticketsAtivos = new HashMap<>();
    }
    public Estacionamento(String nome) {
        this.nome = nome;
        this.endereco = null;
        this.telefone = null;
        this.vagas = new ArrayList<>();
        this.veiculosEstacionados = new ArrayList<>();
        this.ticketsAtivos = new HashMap<>();
    }

    public void gerarVagas(int numColunas, int vagasPorColuna, int tipoVaga) {
        for (int coluna = 0; coluna < numColunas; coluna++) {
            char letraColuna = (char) ('A' + coluna); // Gera a letra da coluna (A, B, C...)
            for (int vagaNum = 1; vagaNum <= vagasPorColuna; vagaNum++) {
                String idVaga = String.format("%c%02d", letraColuna, vagaNum); // Ex: A01, B02, etc.
                
                // Usa a fábrica de vagas para criar a vaga
                Vaga vaga = VagaFactory.criarVaga(tipoVaga, idVaga); 
                if (vaga != null) {
                    adicionarVaga(vaga); // Adiciona a vaga ao estacionamento
                    System.out.println("Vaga " + idVaga + " criada e adicionada.");
                } else {
                    System.out.println("Tipo de vaga inválido. Vaga " + idVaga + " não criada.");
                }
            }
        }
    }

    public void adicionarVaga(Vaga vaga) {
        vagas.add(vaga);
    }

    public void alterarEndereco(String novoEndereco) {
        this.endereco = novoEndereco;
    }

    public void alterarTelefone(String novoTelefone) {
        this.telefone = novoTelefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    // Tenta estacionar um veículo em uma vaga específica
    public boolean estacionarVeiculo(Veiculo veiculo, String idVaga) {
        for (Vaga vaga : vagas) {
            if (vaga.getId().equals(idVaga) && !vaga.isOcupada()) {
                vaga.ocuparVaga(veiculo);
                veiculosEstacionados.add(veiculo);

                // Gerar o ticket de estacionamento com a hora atual
                long horaEntrada = System.currentTimeMillis();
                Cliente cliente = veiculo.getCliente();
                TicketEstacionamento ticket = new TicketEstacionamento(vaga, veiculo, horaEntrada, cliente);
                ticketsAtivos.put(idVaga, ticket); // Adiciona o ticket ativo

                return true; 
            }
        }
        return false; 
    }

    public void limparVagas() {
        vagas.clear(); 
    }
    

    public boolean liberarVaga(String idVaga) {
        for (Vaga vaga : vagas) {
            if (vaga.getId().equals(idVaga) && vaga.isOcupada()) {
                Veiculo veiculo = vaga.getVeiculo();
                vaga.liberarVaga(); // Libera a vaga
                veiculosEstacionados.remove(veiculo); // Remove o veículo da lista
    
                // Processar ticket de saída e calcular o preço
                long horaSaida = System.currentTimeMillis();
                TicketEstacionamento ticket = ticketsAtivos.get(idVaga);
    
                if (ticket != null) {
                    ticket.registrarSaida(horaSaida); // Registra a saída
    
                    double precoTotal = ticket.getPrecoTotal(); // Calcula o preço total
                    System.out.println("Preço total: R$ " + precoTotal);
    
                    // Adiciona o preço ao total arrecadado
                    precoArrecadado += precoTotal;
    
                    ticketsAtivos.remove(idVaga); // Remove o ticket dos ativos
                }
    
                return true; // Sucesso ao liberar a vaga
            }
        }
        return false; // Falha ao liberar a vaga
    }
    

    // Retorna a lista de vagas
    public List<Vaga> getVagas() {
        return vagas;
    }

    // Retorna a lista de veículos estacionados
    public List<Veiculo> getVeiculosEstacionados() {
        return veiculosEstacionados;
    }

    public double getPrecoArrecadado() {
        return precoArrecadado;
    }   

    public String getNome() {
        return nome;
    }
}   