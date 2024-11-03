package controller;

import java.util.List;
import model.ClienteModel;
import model.EstacionamentoModel;
import dao.ClienteDAO;
import dao.EstacionamentoDAO;

public class XulambsController {
    private ClienteDAO clienteDAO;
    private EstacionamentoDAO estacionamentoDAO;

    public XulambsController() {
        this.clienteDAO = new ClienteDAO();
        this.estacionamentoDAO = new EstacionamentoDAO();
    }

    public void adicionarCliente(ClienteModel cliente) {
        clienteDAO.salvarCliente(cliente);
    }

    public boolean removerCliente(String id) {
        return clienteDAO.remover(id);
    }

    public List<ClienteModel> getClientes() {
        return clienteDAO.listarTodos();
    }

    public ClienteModel buscarClientePorId(String id) {
        return clienteDAO.buscarPorId(id);
    }

    public void adicionarEstacionamento(EstacionamentoModel estacionamento) {
        estacionamentoDAO.salvarEstacionamento(estacionamento);
    }

    public boolean removerEstacionamento(String nome) {
        return estacionamentoDAO.removerEstacionamento(nome);
    }

    public List<EstacionamentoModel> getEstacionamentos() {
        return estacionamentoDAO.listarTodos();
    }

    public EstacionamentoModel buscarEstacionamento(String nome) {
        return estacionamentoDAO.buscarPorNome(nome);
    }
}
