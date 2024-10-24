package empresa;

import estacionamento.Estacionamento;
import veiculos.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Xulambs {
    private List<Estacionamento> estacionamentos;  // Lista de estacionamentos
    private List<Cliente> clientes;  // Lista de clientes

    // Construtor que inicializa as listas
    public Xulambs() {
        this.estacionamentos = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    // Método para adicionar um estacionamento à lista
    public void adicionarEstacionamento(Estacionamento estacionamento) {
        estacionamentos.add(estacionamento);
    }

    // Método para remover um estacionamento da lista pelo nome
    public boolean removerEstacionamento(String nome) {
        return estacionamentos.removeIf(estacionamento -> estacionamento.getNome().equalsIgnoreCase(nome));
    }

    // Método para listar todos os estacionamentos
    public List<Estacionamento> getEstacionamentos() {
        return estacionamentos;
    }

    // Método para buscar um estacionamento pelo nome
    public Estacionamento buscarEstacionamento(String nome) {
        for (Estacionamento estacionamento : estacionamentos) {
            if (estacionamento.getNome().equalsIgnoreCase(nome)) {
                return estacionamento;
            }
        }
        return null;
    }

    // Método para adicionar um cliente à lista
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // Método para listar todos os clientes
    public List<Cliente> getClientes() {
        return clientes;
    }
}
