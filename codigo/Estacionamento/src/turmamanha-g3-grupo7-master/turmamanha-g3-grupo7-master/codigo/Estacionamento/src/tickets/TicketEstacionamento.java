package tickets;

import vagas.Vaga;
import veiculos.Veiculo;
import veiculos.Cliente;

public class TicketEstacionamento {
    private Vaga idVaga;
    private Veiculo veiculo;
    private long horaEntrada;
    private long horaSaida;
    private Cliente cliente;

    public TicketEstacionamento(Vaga idVaga, Veiculo veiculo, long horaEntrada, Cliente cliente) {
        this.cliente = cliente;
        this.idVaga = idVaga;
        this.veiculo = veiculo;
        this.horaEntrada = horaEntrada;
    }

    public void registrarSaida(long horaSaida) {
        this.horaSaida = horaSaida;
    }

    public double calcularPrecoTotal(Vaga vaga) {
        int totalMinutos = (int) ((horaSaida - horaEntrada) / 60000); // Convertendo milissegundos para minutos
        return vaga.calcularPreco(totalMinutos);
    }
}
