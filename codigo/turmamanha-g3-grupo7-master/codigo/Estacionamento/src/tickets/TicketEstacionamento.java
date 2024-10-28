package tickets;

import vagas.Vaga;
import veiculos.Cliente;
import veiculos.Veiculo;

public class TicketEstacionamento {
    private Vaga vaga;
    private Veiculo veiculo;
    private long horaEntrada;
    private long horaSaida;
    private Cliente cliente;
    private double precoTotal;

    public TicketEstacionamento(Vaga vaga, Veiculo veiculo, long horaEntrada, Cliente cliente) {
        this.cliente = cliente;
        this.vaga = vaga;
        this.veiculo = veiculo;
        this.horaEntrada = horaEntrada;

    }

    public void registrarSaida(long horaSaida) {
        this.horaSaida = horaSaida;
        this.precoTotal = calcularPrecoTotal(vaga);
    }

    public double calcularPrecoTotal(Vaga vaga) {
        int totalMinutos = (int) ((horaSaida - horaEntrada) / 60000); // Convertendo milissegundos para minutos
        return vaga.calcularPreco(totalMinutos);
    }


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
    public long getHoraEntrada() {
        return horaEntrada;
    }
    public long getHoraSaida() {
        return horaSaida;
    }
    public double getPrecoTotal() {
        return precoTotal;
    }
}
