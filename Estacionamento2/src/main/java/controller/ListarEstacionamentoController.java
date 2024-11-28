package controller;

import dao.EstacionamentoDAO;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListarEstacionamentoController {

    private EstacionamentoDAO estacionamentoDAO;

    public ListarEstacionamentoController() {
        this.estacionamentoDAO = new EstacionamentoDAO();
    }

    public List<EstacionamentoModel> listarEstacionamentos() {
        return estacionamentoDAO.listarEstacionamentos();
    }

    public boolean adicionarEstacionamento(String nome, String endereco, String telefone, int colunas, int vagasPorColuna) {
        EstacionamentoModel estacionamento = new EstacionamentoModel(nome, endereco, telefone);

        int idEstacionamento = estacionamentoDAO.salvarEstacionamento(estacionamento);
        estacionamento.setId(idEstacionamento);

        if (idEstacionamento > 0) {
            gerarVagas(estacionamento, colunas, vagasPorColuna);
            return true;
        }
        return false;
    }

    private void gerarVagas(EstacionamentoModel estacionamento, int colunas, int vagasPorColuna) {
        List<VagaModel> vagas = new ArrayList<>();
        int totalVagas = colunas * vagasPorColuna;

        // Define a proporção de vagas por tipo
        int vagasPCD = totalVagas / 5;  // 20% das vagas para PCD
        int vagasVIP = totalVagas / 10;  // 10% das vagas para VIP
        int vagasIdoso = totalVagas / 5; // 20% das vagas para Idoso

        int vagaAtual = 0;

        for (int c = 0; c < colunas; c++) {
            char letraColuna = (char) ('A' + c);
            for (int v = 1; v <= vagasPorColuna; v++) {
                String idVaga = letraColuna + String.format("%02d", v);

                VagaModel vaga;
                if (vagaAtual < vagasPCD) {
                    vaga = new VagaPCDModel(idVaga);
                } else if (vagaAtual < vagasPCD + vagasIdoso) {
                    vaga = new VagaIdosoModel(idVaga); // Adiciona vagas Idoso
                } else if (vagaAtual < vagasPCD + vagasIdoso + vagasVIP) {
                    vaga = new VagaVIPModel(idVaga);
                } else {
                    vaga = new VagaPadraoModel(idVaga);
                }

                vaga.setOcupada(false);
                vaga.setIdEstacionamento(estacionamento.getId());
                vagas.add(vaga);

                vagaAtual++;
            }
        }

        estacionamentoDAO.salvarVagas(vagas);
    }


    public boolean removerEstacionamento(int id) {
        return estacionamentoDAO.removerEstacionamentoPorId(id);
    }

    public EstacionamentoModel buscarEstacionamentoPorId(int id) {
        return estacionamentoDAO.buscarEstacionamentoPorId(id);
    }

    public List<Object[]> obterRankingEstacionamentos() {
        return EstacionamentoDAO.buscarRankingEstacionamentos();
    }

    public List<Map<String, Object>> getRankingUtilizacao() {
        return estacionamentoDAO.listarRankingUtilizacao();
    }

}
