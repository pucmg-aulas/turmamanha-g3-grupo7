package model;

import java.io.*;
import java.util.List;



public class DadosEstacionamento {

    // Método para salvar a lista de vagas em um arquivo de texto
    public static void salvarVagas(List<Vaga> vagas, String nomeArquivo) {
        // Tenta criar um BufferedWriter para escrever no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Para cada vaga na lista, escreve uma linha no formato: tipo, id, ocupada
            for (Vaga vaga : vagas) {
                writer.write(vaga.getTipo() + "," + vaga.getId() + "," + vaga.isOcupada() + "\n");
            }
            System.out.println("Vagas salvas com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar vagas: " + e.getMessage());
        }
    }

    // Método para carregar a lista de vagas a partir de um arquivo de texto
    public static void carregarVagas(Estacionamento estacionamento, String nomeArquivo) {
        // Primeiro, limpa as vagas existentes no estacionamento
        estacionamento.limparVagas();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            // Lê cada linha do arquivo até o final
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String tipo = partes[0];
                String id = partes[1];
                boolean ocupada = Boolean.parseBoolean(partes[2]);
                System.out.println(tipo);

                // Cria uma nova vaga usando a fábrica de vagas (VagaFactory)
                int tipoInt = Integer.parseInt(tipo);
                Vaga vaga = VagaFactory.criarVaga(tipoInt, id);
                if (ocupada) {
                    vaga.ocuparVaga(new Veiculo("XXX-XXXX", "ClienteID")); // Se a vaga estava ocupada no arquivo,
                                                                           // recria a ocupação com um veículo fictício

                }
                // Adiciona a vaga (ocupada ou livre) ao estacionamento
                estacionamento.adicionarVaga(vaga);
            }
            System.out.println("Vagas carregadas com sucesso do arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao carregar vagas: " + e.getMessage());
        }
    }

    public static void salvarTickets(List<TicketEstacionamento> tickets, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (TicketEstacionamento ticket : tickets) {
                writer.write(ticket.getVaga().getId() + "," + ticket.getVeiculo().getPlaca() + ","
                        + ticket.getDataHoraEntrada() + "," + ticket.getCliente().getId() + "\n");
            }
            System.out.println("Tickets salvos com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar tickets: " + e.getMessage());
        }
    }

    public static void salvarDadosEstacionamento(Estacionamento estacionamento, String nomeArquivoVagas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoVagas))) {
            // Salva os dados básicos do estacionamento no início do arquivo
            writer.write("Nome: " + estacionamento.getNome() + "\n");
            writer.write("Endereço: " + estacionamento.getEndereco() + "\n");
            writer.write("Telefone: " + estacionamento.getTelefone() + "\n");
            writer.write("Vagas:\n");

            // Salva as vagas em seguida
            for (Vaga vaga : estacionamento.getVagas()) {
                writer.write(vaga.getTipo() + "," + vaga.getId() + "," + vaga.isOcupada() + "\n");
            }
            System.out.println("Dados do estacionamento e vagas salvos com sucesso no arquivo: " + nomeArquivoVagas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados do estacionamento: " + e.getMessage());
        }
    }

    public static void salvarClientes(List<Cliente> clientes, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.getId() + "," + cliente.getNome() + "\n");
            }
            System.out.println("Clientes salvos com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    public static void salvarVeiculos(List<Cliente> clientes, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Cliente cliente : clientes) {
                for (Veiculo veiculo : cliente.getVeiculos()) {
                    writer.write(cliente.getId() + "," + veiculo.getPlaca() + "\n");
                }
            }
            System.out.println("Veículos salvos com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar veículos: " + e.getMessage());
        }
    }

}
