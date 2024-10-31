package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketEstacionamento {
    private Vaga vaga;
    private Veiculo veiculo;
    private Cliente cliente;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private double precoTotal;

    // Formato para exibição de data e hora
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Construtor atualizado para incluir LocalDateTime
    public TicketEstacionamento(Vaga vaga, Veiculo veiculo, LocalDateTime dataHoraEntrada, Cliente cliente) {
        this.cliente = cliente;
        this.vaga = vaga;
        this.veiculo = veiculo;
        this.dataHoraEntrada = dataHoraEntrada;
    }

    // Método para registrar a saída
    public void registrarSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
        this.precoTotal = calcularPrecoTotal();
    }

    // Método para calcular o preço total com base na duração
    private double calcularPrecoTotal() {
        if (dataHoraSaida == null || dataHoraSaida.isBefore(dataHoraEntrada)) {
            throw new IllegalStateException("A data/hora de saída não pode ser anterior à entrada.");
        }

        // Calcula a duração total em minutos
        long totalMinutos = Duration.between(dataHoraEntrada, dataHoraSaida).toMinutes();
        return vaga.calcularPreco((int) totalMinutos);
    }

    // Getters
    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getPlacaVeiculo() {
        return veiculo.getPlaca();
    }

    public String getNomeCliente() {
        return cliente.getNome();
    }

    public String getDataHoraEntrada() {
        return dataHoraEntrada.format(formatter);
    }

    public String getDataHoraSaida() {
        return (dataHoraSaida != null) ? dataHoraSaida.format(formatter) : "Ainda não registrado";
    }

    public Vaga getVaga() {
        return vaga;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }
}
