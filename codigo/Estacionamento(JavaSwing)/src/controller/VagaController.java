package controller;

import dao.VagaDAO;
import model.VagaModel;
import model.VagaPadraoModel;
import model.VagaIdosoModel;
import model.VagaPCDModel;
import model.VagaVIPModel;
import view.GerarVagasView;
import view.EditarTipoVagaView;

import java.util.List;

public class VagaController {
    private VagaDAO vagaDAO;

    public VagaController(GerarVagasView gerarVagasView) {
        this.vagaDAO = new VagaDAO();
        gerarVagasView.adicionarListener(e -> gerarVagas(gerarVagasView));
    }

    public VagaController(EditarTipoVagaView editarTipoVagaView) {
        this.vagaDAO = new VagaDAO();
        editarTipoVagaView.addAlterarListener(e -> alterarTipoVaga(editarTipoVagaView));
    }

    private void gerarVagas(GerarVagasView view) {
        String estacionamentoId = view.getId();
        int numLinhas = Integer.parseInt(view.getNumeroLinhas());
        int numColunas = Integer.parseInt(view.getNumeroColunas());

        for (char linha = 'A'; linha < 'A' + numLinhas; linha++) {
            for (int col = 1; col <= numColunas; col++) {
                String idVaga = linha + String.format("%02d", col);
                VagaModel vaga = new VagaPadraoModel(idVaga);
                vagaDAO.salvarVaga(estacionamentoId, vaga);
            }
        }
        view.mostrarMensagem("Vagas geradas com sucesso.");
    }

    private void alterarTipoVaga(EditarTipoVagaView view) {
        String estacionamentoId = view.getEstacionamentoId();
        String vagaId = view.getVagaId();
        String novoTipo = view.getNovoTipo();

        List<VagaModel> vagas = vagaDAO.carregarVagas(estacionamentoId);
        boolean vagaEncontrada = false;

        for (int i = 0; i < vagas.size(); i++) {
            VagaModel vaga = vagas.get(i);
            if (vaga.getId().equals(vagaId)) {
                VagaModel novaVaga;

                switch (novoTipo) {
                    case "Idoso":
                        novaVaga = new VagaIdosoModel(vagaId);
                        break;
                    case "PCD":
                        novaVaga = new VagaPCDModel(vagaId);
                        break;
                    case "VIP":
                        novaVaga = new VagaVIPModel(vagaId);
                        break;
                    default:
                        novaVaga = new VagaPadraoModel(vagaId);
                        break;
                }
                vagas.set(i, novaVaga); // Atualiza a vaga na lista
                vagaEncontrada = true;
                break;
            }
        }

        if (vagaEncontrada) {
            vagaDAO.salvarVagas(estacionamentoId, vagas);
            view.mostrarMensagem("Vaga alterada com sucesso.");
            view.dispose(); // Fecha a tela após sucesso
        } else {
            view.mostrarMensagem("Erro: Vaga não encontrada.");
        }
    }
}
