package controller;

import dao.ClienteDAO;
import model.ClienteModel;
import model.TicketModel;
import model.VeiculoModel;

import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    // Adiciona um cliente e retorna o objeto criado
    public ClienteModel adicionarCliente(String nome) {
        ClienteModel cliente = new ClienteModel(nome, null); // O ID será gerado pelo DAO
        clienteDAO.salvarCliente(cliente);
        return cliente;
    }

    // Adiciona um veículo a um cliente
    public void adicionarVeiculoAoCliente(String idCliente, String placa) {
        VeiculoModel veiculo = new VeiculoModel(placa);
        clienteDAO.adicionarVeiculoAoCliente(idCliente, veiculo);
    }

    // Retorna a lista de todos os clientes e seus veículos
    public List<ClienteModel> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }

    // Remove um cliente pelo ID
    public void removerCliente(String idCliente) {
        clienteDAO.removerCliente(idCliente);
    }

    // Busca um cliente pelo ID
    public ClienteModel buscarClientePorId(String id) {
        return clienteDAO.buscarPorId(id);
    }

    // Busca um cliente pela placa de um veículo
    public ClienteModel buscarClientePorPlaca(String placa) {
        return clienteDAO.buscarClientePorPlaca(placa);
    }

    public List<TicketModel> listarTicketsDoCliente(String idCliente) {
        return clienteDAO.listarTicketsDoCliente(idCliente);
    }

    public List<Object[]> obterRankingClientes() {
        return clienteDAO.calcularRankingClientes();
    }


}
