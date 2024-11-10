package controller;

import model.ClienteModel;
import model.EstacionamentoModel;
import dao.ClienteDAO;
import dao.EstacionamentoDAO;

import java.util.List;

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


    public List<ClienteModel> getClientes() {
        return clienteDAO.listarTodos();
    }

    public ClienteModel buscarClientePorId(String id) {
        return clienteDAO.buscarPorId(id);
    }

    public void adicionarEstacionamento(EstacionamentoModel estacionamento) {
        estacionamentoDAO.salvarEstacionamento(estacionamento);
    }

    public boolean removerEstacionamentoPorId(int id) {
        return estacionamentoDAO.removerEstacionamentoPorId(id);
    }

    public List<EstacionamentoModel> getEstacionamentos() {
        return estacionamentoDAO.listarEstacionamentos();
    }

}
