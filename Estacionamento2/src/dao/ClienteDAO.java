package dao;

import model.ClienteModel;
import model.VeiculoModel;
import persistencia.BancoDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClienteDAO {

    // Método para gerar um novo ID baseado no maior ID existente no banco
    public String gerarId() {
        String sql = "SELECT MAX(id) AS max_id FROM cliente";
        int ultimoId = 0;

        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                ultimoId = rs.getInt("max_id");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao gerar ID: " + e.getMessage());
            e.printStackTrace();
        }

        return String.valueOf(ultimoId + 1); // Retorna o próximo ID
    }

    // Método para salvar um cliente no banco de dados
    public void salvarCliente(ClienteModel cliente) {
        String sql = "INSERT INTO cliente (id, nome, placa) VALUES (?, ?, ?)";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(cliente.getId())); // Converte o ID para int
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getVeiculos().isEmpty() ? null : getPlacasConcatenadas(cliente.getVeiculos()));
            stmt.executeUpdate();

            System.out.println("Cliente salvo no banco de dados: " + cliente.getId() + " - " + cliente.getNome());

        } catch (SQLException e) {
            System.err.println("Erro ao salvar cliente no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para listar todos os clientes do banco de dados
    public List<ClienteModel> listarTodos() {
        List<ClienteModel> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String nome = rs.getString("nome");
                String placasConcatenadas = rs.getString("placa");

                ClienteModel cliente = new ClienteModel(nome, id);

                // Adiciona as placas ao cliente
                if (placasConcatenadas != null && !placasConcatenadas.isEmpty()) {
                    List<String> placas = Arrays.asList(placasConcatenadas.split(","));
                    for (String placa : placas) {
                        cliente.adicionarVeiculo(new VeiculoModel(placa.trim()));
                    }
                }

                clientes.add(cliente);
            }
            System.out.println("Clientes carregados do banco de dados: " + clientes.size());

        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return clientes;
    }

    // Método para adicionar um veículo ao cliente no banco de dados
    public void adicionarVeiculoAoCliente(String clienteId, VeiculoModel veiculo) {
        String sqlSelect = "SELECT placa FROM cliente WHERE id = ?";
        String sqlUpdate = "UPDATE cliente SET placa = ? WHERE id = ?";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement selectStmt = conn.prepareStatement(sqlSelect);
             PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {

            // Obtém as placas existentes
            selectStmt.setInt(1, Integer.parseInt(clienteId));
            ResultSet rs = selectStmt.executeQuery();

            String placasExistentes = null;
            if (rs.next()) {
                placasExistentes = rs.getString("placa");
            }

            // Adiciona a nova placa à lista existente
            String novasPlacas = adicionarPlaca(placasExistentes, veiculo.getPlaca());

            // Atualiza o campo de placas no banco
            updateStmt.setString(1, novasPlacas);
            updateStmt.setInt(2, Integer.parseInt(clienteId));
            updateStmt.executeUpdate();

            System.out.println("Veículo adicionado ao cliente com ID: " + clienteId);

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar veículo ao cliente no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para concatenar uma nova placa às placas existentes
    private String adicionarPlaca(String placasExistentes, String novaPlaca) {
        if (placasExistentes == null || placasExistentes.isEmpty()) {
            return novaPlaca; // Nenhuma placa existente, retorna a nova placa
        } else {
            // Adiciona a nova placa à lista existente, separada por vírgula
            return placasExistentes + "," + novaPlaca;
        }
    }

    // Método para remover um cliente pelo ID
    public void removerCliente(String clienteId) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(clienteId));
            stmt.executeUpdate();

            System.out.println("Cliente removido do banco de dados: " + clienteId);

        } catch (SQLException e) {
            System.err.println("Erro ao remover cliente do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para remover um veículo do cliente
    public void removerVeiculoDoCliente(String clienteId, String placaRemover) {
        String sqlSelect = "SELECT placa FROM cliente WHERE id = ?";
        String sqlUpdate = "UPDATE cliente SET placa = ? WHERE id = ?";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement selectStmt = conn.prepareStatement(sqlSelect);
             PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {

            // Obtém as placas existentes
            selectStmt.setInt(1, Integer.parseInt(clienteId));
            ResultSet rs = selectStmt.executeQuery();

            String placasExistentes = null;
            if (rs.next()) {
                placasExistentes = rs.getString("placa");
            }

            // Remove a placa especificada
            String novasPlacas = removerPlaca(placasExistentes, placaRemover);

            // Atualiza o campo de placas no banco
            updateStmt.setString(1, novasPlacas);
            updateStmt.setInt(2, Integer.parseInt(clienteId));
            updateStmt.executeUpdate();

            System.out.println("Veículo removido do cliente com ID: " + clienteId);

        } catch (SQLException e) {
            System.err.println("Erro ao remover veículo do cliente no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para remover uma placa da lista de placas
    private String removerPlaca(String placasExistentes, String placaRemover) {
        if (placasExistentes == null || placasExistentes.isEmpty()) {
            return null; // Nenhuma placa para remover
        }

        List<String> placas = new ArrayList<>(Arrays.asList(placasExistentes.split(",")));
        placas.removeIf(placa -> placa.trim().equalsIgnoreCase(placaRemover));

        return String.join(",", placas); // Retorna as placas restantes
    }

    public ClienteModel buscarPorId(String id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        ClienteModel cliente = null;

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(id)); // Define o parâmetro ID na consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String placasConcatenadas = rs.getString("placa");

                cliente = new ClienteModel(nome, id);

                // Divide as placas concatenadas e adiciona ao cliente
                if (placasConcatenadas != null && !placasConcatenadas.isEmpty()) {
                    List<String> placas = Arrays.asList(placasConcatenadas.split(","));
                    for (String placa : placas) {
                        cliente.adicionarVeiculo(new VeiculoModel(placa.trim()));
                    }
                }

                System.out.println("Cliente encontrado no banco de dados com ID: " + id);
            } else {
                System.out.println("Nenhum cliente encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return cliente;
    }


    public ClienteModel buscarClientePorPlaca(String placa) {
        String sql = "SELECT * FROM cliente WHERE placa LIKE ?";
        ClienteModel cliente = null;

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + placa + "%"); // Procura a placa parcial ou completa
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String nome = rs.getString("nome");
                String placasConcatenadas = rs.getString("placa");

                cliente = new ClienteModel(nome, id);

                // Divide a string de placas e adiciona ao cliente
                if (placasConcatenadas != null && !placasConcatenadas.isEmpty()) {
                    List<String> placas = Arrays.asList(placasConcatenadas.split(","));
                    for (String p : placas) {
                        cliente.adicionarVeiculo(new VeiculoModel(p.trim()));
                    }
                }

                System.out.println("Cliente encontrado no banco de dados pela placa: " + placa);
            } else {
                System.out.println("Nenhum cliente encontrado para a placa: " + placa);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por placa no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return cliente;
    }


    // Método auxiliar para concatenar placas
    private String getPlacasConcatenadas(List<VeiculoModel> veiculos) {
        StringBuilder sb = new StringBuilder();
        for (VeiculoModel veiculo : veiculos) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(veiculo.getPlaca());
        }
        return sb.toString();
    }
}
