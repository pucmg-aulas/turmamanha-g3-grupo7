package controller;

import model.EstacionamentoModel;
import model.VagaModel;
import model.TicketEstacionamentoModel;
import model.VeiculoModel;

import java.time.LocalDateTime;

public class EstacionamentoController {
    private EstacionamentoModel estacionamento;

    public EstacionamentoController(EstacionamentoModel estacionamento) {
        this.estacionamento = estacionamento;
    }

    public boolean estacionarVeiculo(VeiculoModel veiculo, String idVaga) {
        for (VagaModel vaga : estacionamento.getVagas()) {
            if (vaga.getId().equals(idVaga) && !vaga.isOcupada()) {
                vaga.ocuparVaga(veiculo);
                estacionamento.adicionarVeiculoEstacionado(veiculo);

                LocalDateTime horaEntrada = LocalDateTime.now();
                TicketEstacionamentoModel ticket = new TicketEstacionamentoModel(vaga, veiculo, horaEntrada, veiculo.getCliente());
                estacionamento.adicionarTicketAtivo(idVaga, ticket);

                return true;
            }
        }
        return false;
    }

    public boolean liberarVaga(String idVaga) {
        for (VagaModel vaga : estacionamento.getVagas()) {
            if (vaga.getId().equals(idVaga) && vaga.isOcupada()) {
                VeiculoModel veiculo = vaga.getVeiculo();
                vaga.liberarVaga();
                estacionamento.removerVeiculoEstacionado(veiculo);

                TicketEstacionamentoModel ticket = estacionamento.removerTicketAtivo(idVaga);
                if (ticket != null) {
                    LocalDateTime horaSaida = LocalDateTime.now();
                    ticket.registrarSaida(horaSaida);
                    double precoTotal = ticket.getPrecoTotal();
                    estacionamento.incrementarPrecoArrecadado(precoTotal);
                }
                return true;
            }
        }
        return false;
    }

    public double calcularValorMedioUtilizacao() {
        int totalTickets = estacionamento.getTicketsAtivos().size();
        if (totalTickets == 0) {
            return 0.0;
        }

        double valorTotal = estacionamento.getPrecoArrecadado();
        return valorTotal / totalTickets;
    }
}
