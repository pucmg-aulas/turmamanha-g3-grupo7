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

    public boolean liberarVaga(String idVaga) {
        // Buscar a vaga correspondente
        VagaModel vagaSelecionada = estacionamento.getVagas().stream()
                .filter(vaga -> vaga.getId().equals(idVaga))
                .findFirst()
                .orElse(null);

        if (vagaSelecionada == null || !vagaSelecionada.isOcupada()) {
            return false; // Vaga inválida ou já está livre
        }

        // Registrar saída no ticket
        boolean saidaRegistrada = ticketDAO.registrarSaida(idVaga, estacionamento.getId());

        if (saidaRegistrada) {
            // Atualizar o status da vaga para livre
            vagaSelecionada.setOcupada(false);
            boolean vagaAtualizada = VagaDAO.atualizarStatusVaga(idVaga, false, estacionamento.getId());

            if (vagaAtualizada) {
                return true;
            } else {
                System.err.println("Falha ao atualizar o status da vaga.");
            }
        } else {
            System.err.println("Falha ao registrar saída do ticket.");
        }

        return false;
    }


}
