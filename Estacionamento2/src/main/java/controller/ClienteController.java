package controller;

import dao.ClienteDAO;
import dao.TicketDAO;
import dao.VagaDAO;
import model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EstacionamentoController {
    private EstacionamentoModel estacionamento;
    private TicketDAO ticketDAO;


    public EstacionamentoController(EstacionamentoModel estacionamento) {
        this.estacionamento = estacionamento;
        this.ticketDAO = new TicketDAO();

    }

    public double getValorMedioUtilizacao() {
        return ticketDAO.calcularValorMedioUtilizacao(estacionamento.getId());
    }

    public double getArrecadacaoMensal(int mes, int ano) {
        TicketDAO ticketDAO = new TicketDAO();
        return ticketDAO.calcularArrecadacaoMensal(estacionamento.getId(), mes, ano);
    }

    public double getArrecadacaoTotal() {
        return ticketDAO.calcularArrecadacaoTotal(estacionamento.getId());
    }

    public boolean estacionarVeiculo(String idVaga, String placa) {
        // Verificar se a vaga está disponível
        VagaModel vagaSelecionada = estacionamento.getVagas().stream()
                .filter(vaga -> vaga.getId().equals(idVaga))
                .findFirst()
                .orElse(null);

        if (vagaSelecionada == null || vagaSelecionada.isOcupada()) {
            System.err.println("Vaga inválida ou já ocupada.");
            return false; // Vaga inválida ou ocupada
        }

        // Buscar o cliente pela placa
        ClienteDAO clienteDAO = new ClienteDAO(); // Nova instância do DAO
        ClienteModel cliente = clienteDAO.buscarClientePorPlaca(placa);

        if (cliente == null) {
            System.err.println("Nenhum cliente encontrado para a placa: " + placa);
            return false; // Cliente não encontrado
        }

        int idCliente = Integer.parseInt(cliente.getId());

        // Criar o ticket
        TicketModel ticket = new TicketModel(
                0, // ID gerado automaticamente no banco
                estacionamento.getId(),
                idVaga,
                idCliente,
                LocalDateTime.now(),
                null, // Sem horário de saída
                BigDecimal.ZERO, // Custo será calculado no momento da saída
                placa
        );

        // Salvar o ticket no banco
        TicketDAO ticketDAO = new TicketDAO(); // Nova instância do DAO
        boolean salvo = ticketDAO.salvarTicket(ticket);

        if (salvo) {
            // Atualizar o status da vaga para "ocupada"
            VagaDAO vagaDAO = new VagaDAO(); // Nova instância do DAO
            boolean vagaAtualizada = vagaDAO.atualizarStatusVaga(idVaga, true, estacionamento.getId());

            if (vagaAtualizada) {
                System.out.println("Veículo estacionado com sucesso! Ticket salvo.");
                return true;
            } else {
                System.err.println("Falha ao atualizar o status da vaga.");
            }
        } else {
            System.err.println("Falha ao salvar o ticket no banco de dados.");
        }

        return false;
    }

    public List<TicketModel> getTicketsAtivos() {
        return ticketDAO.buscarTicketsAtivos(estacionamento.getId());
    }

    public String getVeiculoPorVaga(String idVaga) {
        return ticketDAO.buscarVeiculoPorVaga(idVaga, estacionamento.getId());
    }

    public Double liberarVaga(String idVaga) {
        VagaModel vagaSelecionada = buscarVagaPorId(idVaga);

        if (vagaSelecionada == null || !vagaSelecionada.isOcupada()) {
            System.err.println("Vaga inválida ou já está livre.");
            return null;
        }

        // Buscar o ticket para obter a entrada
        TicketModel ticket = ticketDAO.buscarTicketPorVaga(idVaga, estacionamento.getId());
        if (ticket == null || ticket.getEntrada() == null) {
            System.err.println("Ticket não encontrado para a vaga.");
            return null;
        }

        // Calcular o tempo de uso e o preço
        LocalDateTime saida = LocalDateTime.now();
        long minutos = java.time.Duration.between(ticket.getEntrada(), saida).toMinutes();
        double preco = vagaSelecionada.calcularPreco((int) minutos);

        // Registrar a saída no DAO
        boolean saidaRegistrada = ticketDAO.registrarSaida(idVaga, estacionamento.getId(), preco);

        if (saidaRegistrada) {
            vagaSelecionada.liberarVaga(); // Atualizar o estado da vaga no modelo

            // Persistir a alteração no banco de dados
            VagaDAO vagaDAO = new VagaDAO();
            boolean vagaAtualizada = vagaDAO.atualizarStatusVaga(idVaga, false, estacionamento.getId());

            if (!vagaAtualizada) {
                System.err.println("Erro ao atualizar o status da vaga no banco de dados.");
            }

            System.out.printf("Vaga liberada com sucesso! Tempo: %d minutos. Preço: R$ %.2f%n", minutos, preco);
            return preco; // Retorna o preço calculado
        } else {
            System.err.println("Erro ao registrar saída.");
            return null;
        }
    }

    // Buscar todos os tickets fechados (com saída registrada)
    public List<TicketModel> getTicketsFechados() {
        return ticketDAO.buscarTicketsFechados(estacionamento.getId());
    }

    public VagaModel buscarVagaPorId(String idVaga) {
        return estacionamento.getVagas().stream()
                .filter(vaga -> vaga.getId().equals(idVaga))
                .findFirst()
                .orElse(null);
    }

}
