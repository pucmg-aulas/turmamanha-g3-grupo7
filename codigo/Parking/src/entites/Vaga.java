package entites;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Vaga {
    private String id;
    private boolean status; // true = ocupada, false = disponível
    private LocalDateTime horaOcupacao;
    private static final double limiteDiario = 50.0;
    private static final double cobrancaPorFracao = 5.0;

    public Vaga(String id, boolean status) {
        this.id = id;
        this.status = status;
        this.horaOcupacao = null;
    }

    public boolean isOcupada() {
        return status;
    }

    public void ocupar() {
        if (!status) { // Se estiver disponível (status == false)
            this.status = true; // Marca como ocupada
            this.horaOcupacao = LocalDateTime.now(); // Salva a hora atual
            System.out.println("Vaga " + id + " ocupada às " + horaOcupacao);
        } else {
            System.out.println("A vaga já está ocupada!");
        }
    }

    public void desocupar() {
        if (status) { // Se estiver ocupada
            this.status = false; // Marca como disponível
            this.horaOcupacao = null; // Limpa o horário de ocupação
            System.out.println("Vaga " + id + " desocupada.");
        } else {
            System.out.println("A vaga já está desocupada!");
        }
    }

    public double cobrarCliente(double descontoPercentual) {
        if (status && horaOcupacao != null) { // Se a vaga está ocupada
            LocalDateTime agora = LocalDateTime.now();
            long minutosOcupados = ChronoUnit.MINUTES.between(horaOcupacao, agora);

            // Calcula o valor a ser cobrado
            double valorCobrado = calcularValor(minutosOcupados);

            // Aplica o desconto, se houver
            if (descontoPercentual > 0) {
                valorCobrado -= (valorCobrado * descontoPercentual / 100);
            }

            // Desocupa a vaga após a cobrança
            desocupar();
            return valorCobrado;
        } else {
            System.out.println("A vaga não está ocupada!");
            return 0;
        }
    }

    private double calcularValor(long minutosOcupados) {
        // Calcula o número de frações de 15 minutos
        int fracoes = (int) Math.ceil(minutosOcupados / 15.0);

        // Calcula o valor total baseado nas frações
        double valor = fracoes * cobrancaPorFracao;

        // Limita o valor ao limite diário de R$50,00
        return Math.min(valor, limiteDiario);
    }

    public String getId() {
        return id;
    }

}
