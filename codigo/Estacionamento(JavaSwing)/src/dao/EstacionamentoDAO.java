package dao;

import model.EstacionamentoModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EstacionamentoDAO {
    private static final String DIRETORIO_DATA = "data";
    private static final String ARQUIVO_ESTACIONAMENTOS = DIRETORIO_DATA + "/estacionamentos.txt";

    public EstacionamentoDAO() {
        // Cria o diretório 'data' se ele não existir
        File directory = new File(DIRETORIO_DATA);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdir();
            System.out.println("Diretório 'data' criado: " + dirCreated);
        }

        File arquivo = new File(ARQUIVO_ESTACIONAMENTOS);
        try {
            if (!arquivo.exists()) {
                boolean fileCreated = arquivo.createNewFile();
                System.out.println("Arquivo 'estacionamentos.txt' criado: " + fileCreated);
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo 'estacionamentos.txt': " + e.getMessage());
        }
    }

    private int gerarId() {
        int ultimoId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_ESTACIONAMENTOS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length > 0) {
                    try {
                        int id = Integer.parseInt(data[0]); // Tenta converter o primeiro elemento para um número inteiro
                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Linha ignorada ao gerar ID: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o último ID: " + e.getMessage());
        }

        return ultimoId + 1; // Retorna o próximo ID sequencial
    }

    public void salvarEstacionamento(EstacionamentoModel estacionamento) {
        int novoId = gerarId(); // Gera um novo ID para o estacionamento
        estacionamento.setId(novoId);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ESTACIONAMENTOS, true))) {
            writer.write(novoId + ";" + estacionamento.getNome() + ";" + estacionamento.getEndereco() + ";" + estacionamento.getTelefone());
            writer.newLine();
            System.out.println("Estacionamento salvo: ID=" + novoId + ", Nome=" + estacionamento.getNome());
        } catch (IOException e) {
            System.err.println("Erro ao salvar estacionamento: " + e.getMessage());
        }
    }

    public boolean removerEstacionamentoPorId(int id) {
        List<EstacionamentoModel> estacionamentos = listarEstacionamentos();
        boolean removed = estacionamentos.removeIf(estacionamento -> estacionamento.getId() == id);

        if (removed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ESTACIONAMENTOS))) {
                for (EstacionamentoModel estacionamento : estacionamentos) {
                    writer.write(estacionamento.getId() + ";" + estacionamento.getNome() + ";" + estacionamento.getEndereco() + ";" + estacionamento.getTelefone());
                    writer.newLine();
                }
                System.out.println("Estacionamento com ID " + id + " removido.");
            } catch (IOException e) {
                System.err.println("Erro ao atualizar o arquivo de estacionamentos: " + e.getMessage());
            }
        } else {
            System.out.println("Estacionamento com ID " + id + " não encontrado.");
        }

        return removed;
    }

    public List<EstacionamentoModel> listarEstacionamentos() {
        List<EstacionamentoModel> estacionamentos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_ESTACIONAMENTOS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 4) { // Inclui ID no arquivo
                    try {
                        int id = Integer.parseInt(data[0]);
                        String nome = data[1];
                        String endereco = data[2];
                        String telefone = data[3];
                        EstacionamentoModel estacionamento = new EstacionamentoModel(id, nome, endereco, telefone);
                        estacionamentos.add(estacionamento);
                    } catch (NumberFormatException e) {
                        System.err.println("Linha ignorada ao listar estacionamentos: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler os estacionamentos: " + e.getMessage());
        }

        return estacionamentos;
    }
}
