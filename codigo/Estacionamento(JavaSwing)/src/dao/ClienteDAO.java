package dao;

import model.ClienteModel;
import model.VeiculoModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static final String DIRECTORY = "data";
    private static final String FILE_NAME = DIRECTORY + "/clientes.txt";

    public ClienteDAO() {
        // Verifica se o diretório 'data' existe; se não, cria-o
        File directory = new File(DIRECTORY);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdir();
            System.out.println("Diretório 'data' criado: " + dirCreated);
        }

        // Verifica se o arquivo 'clientes.txt' existe; se não, cria-o
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                System.out.println("Arquivo 'clientes.txt' criado: " + fileCreated);
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo 'clientes.txt': " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String gerarId() {
        int ultimoId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 1) {
                    int id = Integer.parseInt(data[0]);
                    if (id > ultimoId) {
                        ultimoId = id;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de clientes não encontrado. Iniciando com ID 1.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o último ID: " + e.getMessage());
            e.printStackTrace();
        }

        return String.valueOf(ultimoId + 1); // Retorna o próximo ID sequencial
    }

    public void salvarCliente(ClienteModel cliente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            StringBuilder linhaCliente = new StringBuilder(cliente.getId() + ";" + cliente.getNome());
            if (!cliente.getVeiculos().isEmpty()) {
                linhaCliente.append(";");
                List<String> placas = new ArrayList<>();
                for (VeiculoModel veiculo : cliente.getVeiculos()) {
                    placas.add(veiculo.getPlaca());
                }
                linhaCliente.append(String.join(",", placas));
            }
            writer.write(linhaCliente.toString());
            writer.newLine();
            System.out.println("Cliente salvo: " + cliente.getId() + " - " + cliente.getNome()); // Log de confirmação
        } catch (IOException e) {
            System.err.println("Erro ao salvar o cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void adicionarVeiculoAoCliente(String clienteId, VeiculoModel veiculo) {
        List<ClienteModel> clientes = listarTodos();
        boolean clienteEncontrado = false;

        for (ClienteModel cliente : clientes) {
            if (cliente.getId().equals(clienteId)) {
                cliente.adicionarVeiculo(veiculo); // Adiciona o veículo à lista do cliente
                clienteEncontrado = true;
                break;
            }
        }

        if (clienteEncontrado) {
            // Reescreve o arquivo com o cliente atualizado
            atualizarArquivo(clientes);
            System.out.println("Veículo adicionado ao cliente com ID: " + clienteId);
        } else {
            System.out.println("Cliente com ID " + clienteId + " não encontrado.");
        }
    }

    public boolean removerVeiculoDoCliente(String clienteId, String placa) {
        List<ClienteModel> clientes = listarTodos();
        boolean veiculoRemovido = false;

        for (ClienteModel cliente : clientes) {
            if (cliente.getId().equals(clienteId)) {
                veiculoRemovido = cliente.getVeiculos().removeIf(veiculo -> veiculo.getPlaca().equalsIgnoreCase(placa));
                break;
            }
        }

        if (veiculoRemovido) {
            atualizarArquivo(clientes);
            System.out.println("Veículo removido: " + placa + " do cliente com ID: " + clienteId);
        } else {
            System.out.println("Veículo com placa " + placa + " não encontrado para o cliente com ID " + clienteId);
        }

        return veiculoRemovido;
    }

    private void atualizarArquivo(List<ClienteModel> clientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ClienteModel cliente : clientes) {
                StringBuilder linhaCliente = new StringBuilder(cliente.getId() + ";" + cliente.getNome());
                if (!cliente.getVeiculos().isEmpty()) {
                    linhaCliente.append(";");
                    List<String> placas = new ArrayList<>();
                    for (VeiculoModel veiculo : cliente.getVeiculos()) {
                        placas.add(veiculo.getPlaca());
                    }
                    linhaCliente.append(String.join(",", placas));
                }
                writer.write(linhaCliente.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo de clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ClienteModel> listarTodos() {
        List<ClienteModel> clientes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 2) {
                    String id = data[0];
                    String nome = data[1];
                    ClienteModel cliente = new ClienteModel(nome, id);

                    // Verifica se há veículos associados
                    if (data.length == 3) {
                        String[] veiculos = data[2].split(",");
                        for (String placa : veiculos) {
                            cliente.adicionarVeiculo(new VeiculoModel(placa));
                        }
                    }
                    clientes.add(cliente);
                }
            }
            System.out.println("Clientes carregados: " + clientes.size()); // Log de confirmação
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de clientes não encontrado. Criando novo arquivo.");
        } catch (IOException e) {
            System.err.println("Erro ao ler os clientes: " + e.getMessage());
            e.printStackTrace();
        }

        return clientes;
    }

    // Método para buscar um cliente por ID
    public ClienteModel buscarPorId(String id) {
        return listarTodos().stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}