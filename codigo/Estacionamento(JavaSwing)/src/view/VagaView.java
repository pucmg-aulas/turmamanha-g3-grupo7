package view;

import model.VagaModel;

public class VagaView {
    public void mostrarDetalhesVaga(VagaModel vaga) {
        System.out.println("ID da Vaga: " + vaga.getId());
        System.out.println("Tipo de Vaga: " + vaga.getTipo());
        System.out.println("Status: " + (vaga.isOcupada() ? "Ocupada" : "Livre"));
        if (vaga.isOcupada() && vaga.getVeiculo() != null) {
            System.out.println("Veículo Estacionado: " + vaga.getVeiculo().getPlaca());
        }
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}
