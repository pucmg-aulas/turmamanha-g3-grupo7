package controller;

import model.VagaModel;
import model.VeiculoModel;
import view.VagaView;

public class VagaController {
    private VagaModel vaga;
    private VagaView view;

    public VagaController(VagaModel vaga, VagaView view) {
        this.vaga = vaga;
        this.view = view;
    }

    public void ocuparVaga(VeiculoModel veiculo) {
        if (!vaga.isOcupada()) {
            vaga.ocuparVaga(veiculo);
            view.mostrarMensagem("Vaga ocupada com sucesso pelo veículo: " + veiculo.getPlaca());
        } else {
            view.mostrarMensagem("Erro: A vaga já está ocupada.");
        }
    }

    public void liberarVaga() {
        if (vaga.isOcupada()) {
            vaga.liberarVaga();
            view.mostrarMensagem("Vaga liberada com sucesso.");
        } else {
            view.mostrarMensagem("Erro: A vaga já está livre.");
        }
    }

    public void mostrarDetalhesVaga() {
        view.mostrarDetalhesVaga(vaga);
    }
}
