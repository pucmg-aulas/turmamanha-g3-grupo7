package Empresa;

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

    // Método para listar todos os estacionamentos
    public List<Estacionamento> getEstacionamentos() {
        return estacionamentos;
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
