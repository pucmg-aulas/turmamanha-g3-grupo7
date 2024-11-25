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

    public ClienteModel adicionarCliente(String nome) {
        ClienteModel cliente = new ClienteModel(nome, null); // O ID será gerado pelo DAO
        clienteDAO.salvarCliente(cliente);
        return cliente;
    }

    public void adicionarVeiculoAoCliente(String idCliente, String placa) {
        VeiculoModel veiculo = new VeiculoModel(placa);
        clienteDAO.adicionarVeiculoAoCliente(idCliente, veiculo);
    }

    public List<ClienteModel> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }

    public void removerCliente(String idCliente) {
        clienteDAO.removerCliente(idCliente);
    }

    public ClienteModel buscarClientePorId(String id) {
        return clienteDAO.buscarPorId(id);
    }

    public ClienteModel buscarClientePorPlaca(String placa) {
        return clienteDAO.buscarClientePorPlaca(placa);
    }

    public List<TicketModel> listarTicketsDoCliente(String idCliente) {
        return clienteDAO.listarTicketsDoCliente(idCliente);
    }

    public List<ClienteModel> listarRankingClientes(int mes, int ano) {
        return clienteDAO.obterRankingClientes(mes, ano);
    }

}
