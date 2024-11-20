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
            if (dirCreated) {
                System.out.println("Diretório 'data' criado.");
            }
        }

        // Verifica se o arquivo 'clientes.txt' existe; se não, cria-o
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    System.out.println("Arquivo 'clientes.txt' criado.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo 'clientes.txt': " + e.getMessage());
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
        } catch (IOException e) {
            System.err.println("Erro ao gerar ID: " + e.getMessage());
        }

        return String.valueOf(ultimoId + 1);
    }

    public void salvarCliente(ClienteModel cliente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            StringBuilder linhaCliente = new StringBuilder(cliente.getId() + ";" + cliente.getNome());
            if (!cliente.getVeiculos().isEmpty()) {
                linhaCliente.append(";").append(String.join(",", cliente.getVeiculos()));
            }
            writer.write(linhaCliente.toString());
            writer.newLine();
            System.out.println("Cliente salvo: " + cliente.getId() + " - " + cliente.getNome());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o cliente: " + e.getMessage());
        }
    }


    public void removerCliente(String clienteId) {
        List<ClienteModel> clientes = listarTodos();
        clientes.removeIf(cliente -> cliente.getId().equals(clienteId));
        atualizarArquivo(clientes);
        System.out.println("Cliente removido: " + clienteId);
    }

    public void adicionarVeiculoAoCliente(String clienteId, VeiculoModel veiculo) {
        List<ClienteModel> clientes = listarTodos();

        for (ClienteModel cliente : clientes) {
            if (cliente.getId().equals(clienteId)) {
                veiculo.setCliente(cliente); // Associa o cliente ao veículo
                cliente.adicionarVeiculo(veiculo);
                atualizarArquivo(clientes);
                System.out.println("Veículo " + veiculo.getPlaca() + " adicionado ao cliente com ID: " + clienteId);
                return;
            }
        }

        System.out.println("Cliente com ID " + clienteId + " não encontrado.");
    }

    public boolean removerVeiculoDoCliente(String clienteId, String placa) {
        List<ClienteModel> clientes = listarTodos();

        for (ClienteModel cliente : clientes) {
            if (cliente.getId().equals(clienteId)) {
                boolean veiculoRemovido = cliente.getVeiculos()
                        .removeIf(veiculo -> veiculo.getPlaca().equalsIgnoreCase(placa));
                if (veiculoRemovido) {
                    atualizarArquivo(clientes);
                    System.out.println("Veículo " + placa + " removido do cliente com ID: " + clienteId);
                }
                return veiculoRemovido;
            }
        }

        System.out.println("Cliente ou veículo não encontrado.");
        return false;
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

                    if (data.length == 3) {
                        String[] placas = data[2].split(",");
                        for (String placa : placas) {
                            cliente.adicionarVeiculo(new VeiculoModel(placa, cliente));
                        }
                    }

                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    public ClienteModel buscarClientePorId(String id) {
        return listarTodos().stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public ClienteModel buscarClientePorPlaca(String placa) {
        for (ClienteModel cliente : listarTodos()) {
            for (VeiculoModel veiculo : cliente.getVeiculos()) {
                if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                    return cliente;
                }
            }
        }
        return null;
    }

    public void atualizarCliente(ClienteModel cliente) {
        List<ClienteModel> clientes = listarTodos();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(cliente.getId())) {
                clientes.set(i, cliente);
                atualizarArquivo(clientes);
                System.out.println("Cliente atualizado: " + cliente.getId());
                return;
            }
        }

        System.out.println("Cliente não encontrado para atualização: " + cliente.getId());
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
        }
    }
}
