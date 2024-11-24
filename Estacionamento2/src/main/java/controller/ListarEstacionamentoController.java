package controller;

import dao.EstacionamentoDAO;
import model.*;
import dao.*;

import java.util.ArrayList;
import java.util.List;

public class ListarEstacionamentoController {

    private EstacionamentoDAO estacionamentoDAO;

    public ListarEstacionamentoController() {
        this.estacionamentoDAO = new EstacionamentoDAO();
    }

    public List<EstacionamentoModel> listarEstacionamentos() {
        return estacionamentoDAO.listarEstacionamentos();
    }

    private VagaDAO vagaDAO;

    public void adicionarEstacionamento(EstacionamentoModel estacionamento, int colunas, int vagasPorColuna) {
        // Salvar estacionamento no banco e obter o ID gerado
        int idEstacionamento = estacionamentoDAO.salvarEstacionamento(estacionamento);
        estacionamento.setId(idEstacionamento); // Atualizar o ID no modelo

        // Gerar vagas
        List<VagaModel> vagas = new ArrayList<>();
        int totalVagas = colunas * vagasPorColuna;
        int vagasPCD = totalVagas / 10; // 10% para PCD
        int vagasVIP = totalVagas / 20; // 5% para VIP

        int vagaAtual = 0;
        for (int c = 0; c < colunas; c++) {
            char letraColuna = (char) ('A' + c);
            for (int v = 1; v <= vagasPorColuna; v++) {
                String idVaga = letraColuna + String.format("%02d", v); // Gerar ID único

                VagaModel vaga;
                if (vagaAtual < vagasPCD) {
                    vaga = new VagaPCDModel(idVaga);
                } else if (vagaAtual < vagasPCD + vagasVIP) {
                    vaga = new VagaVIPModel(idVaga);
                } else {
                    vaga = new VagaPadraoModel(idVaga);
                }

                vaga.setOcupada(false);
                vaga.setIdEstacionamento(idEstacionamento); // Associe ao ID do estacionamento correto
                vagas.add(vaga);

                vagaAtual++;
            }
        }

        // Salvar vagas no banco
        estacionamentoDAO.salvarVagas(vagas);
    }



    public void ocuparVaga(VagaModel vaga, VeiculoModel veiculo) {
        if (!vaga.isOcupada()) {
            vaga.ocuparVaga(veiculo);
            vagaDAO.atualizarOcupacaoVaga(vaga.getId(), true);
        } else {
            System.out.println("A vaga já está ocupada.");
        }
    }

    public void liberarVaga(VagaModel vaga) {
        if (vaga.isOcupada()) {
            vaga.liberarVaga();
            vagaDAO.atualizarOcupacaoVaga(vaga.getId(), false);
        } else {
            System.out.println("A vaga já está liberada.");
        }
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
