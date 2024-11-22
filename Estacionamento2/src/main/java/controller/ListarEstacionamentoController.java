package controller;

import dao.EstacionamentoDAO;
import model.EstacionamentoModel;

import java.util.List;

public class ListarEstacionamentoController {

    private EstacionamentoDAO estacionamentoDAO;

    public ListarEstacionamentoController() {
        this.estacionamentoDAO = new EstacionamentoDAO();
    }

    public List<EstacionamentoModel> listarEstacionamentos() {
        return estacionamentoDAO.listarEstacionamentos();
    }

    public void adicionarEstacionamento(EstacionamentoModel estacionamento) {
        estacionamentoDAO.salvarEstacionamento(estacionamento);
    }

    public boolean removerEstacionamento(int id) {
        return estacionamentoDAO.removerEstacionamentoPorId(id);
    }

    public EstacionamentoModel buscarEstacionamentoPorId(int id) {
        List<EstacionamentoModel> estacionamentos = listarEstacionamentos();
        for (EstacionamentoModel estacionamento : estacionamentos) {
            if (estacionamento.getId() == id) {
                return estacionamento;
            }
        }
        return null;
    }
}
