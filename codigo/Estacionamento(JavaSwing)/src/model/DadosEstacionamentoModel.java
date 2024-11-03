package model;

import java.io.*;
import java.util.List;

public class DadosEstacionamentoModel {

    public static void salvarVagas(List<VagaModel> vagas, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (VagaModel vaga : vagas) {
                writer.write(vaga.getTipo() + "," + vaga.getId() + "," + vaga.isOcupada() + "\n");
            }
            System.out.println("Vagas salvas com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar vagas: " + e.getMessage());
        }
    }

    public static void carregarVagas(EstacionamentoModel estacionamento, String nomeArquivo) {
        estacionamento.limparVagas();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 3) {
                    System.out.println("Linha malformada: " + linha);
                    continue;
                }
                String tipo = partes[0];
                String id = partes[1];
                boolean ocupada = Boolean.parseBoolean(partes[2]);
                int tipoInt = Integer.parseInt(tipo);
                VagaModel vaga = VagaFactoryModel.criarVaga(tipoInt, id);
                if (ocupada) {
                    vaga.ocuparVaga(new VeiculoModel("XXX-XXXX", new ClienteModel( "Cliente nome","ClienteID")));
                }
                estacionamento.adicionarVaga(vaga);
            }
            System.out.println("Vagas carregadas com sucesso do arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao carregar vagas: " + e.getMessage());
        }
    }
    

    public static void salvarTickets(List<TicketEstacionamentoModel> tickets, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (TicketEstacionamentoModel ticket : tickets) {
                writer.write(ticket.getVaga().getId() + "," + ticket.getVeiculo().getPlaca() + "," +
                        ticket.getDataHoraEntrada() + "," + ticket.getCliente().getId() + "\n");
            }
            System.out.println("Tickets salvos com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar tickets: " + e.getMessage());
        }
    }

    public static void salvarDadosEstacionamento(EstacionamentoModel estacionamento, String nomeArquivoVagas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoVagas))) {
            writer.write("Nome: " + estacionamento.getNome() + "\n");
            writer.write("Endereço: " + estacionamento.getEndereco() + "\n");
            writer.write("Telefone: " + estacionamento.getTelefone() + "\n");
            writer.write("Vagas:\n");

            for (VagaModel vaga : estacionamento.getVagas()) {
                writer.write(vaga.getTipo() + "," + vaga.getId() + "," + vaga.isOcupada() + "\n");
            }
            System.out.println("Dados do estacionamento e vagas salvos com sucesso no arquivo: " + nomeArquivoVagas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados do estacionamento: " + e.getMessage());
        }
    }

    public static void salvarClientes(List<ClienteModel> clientes, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (ClienteModel cliente : clientes) {
                writer.write(cliente.getId() + "," + cliente.getNome() + "\n");
            }
            System.out.println("Clientes salvos com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    public static void salvarVeiculos(List<ClienteModel> clientes, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (ClienteModel cliente : clientes) {
                for (VeiculoModel veiculo : cliente.getVeiculos()) {
                    writer.write(cliente.getId() + "," + veiculo.getPlaca() + "\n");
                }
            }
            System.out.println("Veículos salvos com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar veículos: " + e.getMessage());
        }
    }
}
