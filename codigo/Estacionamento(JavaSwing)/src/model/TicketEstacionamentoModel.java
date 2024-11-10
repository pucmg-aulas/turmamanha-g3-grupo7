package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class TicketEstacionamentoModel {
    private VagaModel vaga;
    private VeiculoModel veiculo;
    private ClienteModel cliente;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private double precoTotal;

    public TicketEstacionamentoModel(VagaModel vaga, VeiculoModel veiculo, LocalDateTime dataHoraEntrada, ClienteModel cliente) {
        this.vaga = vaga;
        this.veiculo = veiculo;
        this.dataHoraEntrada = dataHoraEntrada;
        this.cliente = cliente;
    }

    public void registrarSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
        this.precoTotal = calcularPrecoTotal();
    }

    private double calcularPrecoTotal() {
        if (dataHoraSaida == null || dataHoraSaida.isBefore(dataHoraEntrada)) {
            throw new IllegalStateException("A data/hora de saída não pode ser anterior à entrada.");
        }

        long totalMinutos = Duration.between(dataHoraEntrada, dataHoraSaida).toMinutes();
        return vaga.calcularPreco((int) totalMinutos); 
    }

    public VagaModel getVaga() {
        return vaga;
    }

    public VeiculoModel getVeiculo() {
        return veiculo;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    public double getPrecoTotal() {
        if (dataHoraSaida == null) {
        }
        return precoTotal;
    }
    
}
