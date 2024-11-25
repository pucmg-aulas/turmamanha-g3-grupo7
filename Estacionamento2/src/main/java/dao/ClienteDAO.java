package dao;

import model.ClienteModel;
import model.TicketModel;
import model.VeiculoModel;
import persistencia.BancoDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Método para gerar um novo ID baseado no maior ID existente no banco
    public String gerarId() {
        String sql = "SELECT COALESCE(MAX(id_cliente), 0) + 1 AS novo_id FROM cliente";
        int novoId = 0;

        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                novoId = rs.getInt("novo_id");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao gerar ID: " + e.getMessage());
        }

        return String.valueOf(novoId); // Retorna o próximo ID
    }

    // Método para salvar um cliente no banco de dados
    public void salvarCliente(ClienteModel cliente) {
        String sql = "INSERT INTO cliente (nome) VALUES (?) RETURNING id_cliente";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente.setId(String.valueOf(rs.getInt("id_cliente"))); // Define o ID gerado no cliente
                System.out.println("Cliente salvo com ID: " + cliente.getId());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Método para salvar um veículo no banco de dados, vinculado a um cliente
    // Método para adicionar um veículo ao cliente no banco de dados
    public void adicionarVeiculoAoCliente(String idCliente, VeiculoModel veiculo) {
        String sql = "INSERT INTO veiculo (placa, id_cliente) VALUES (?, ?)";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setInt(2, Integer.parseInt(idCliente));
            stmt.executeUpdate();

            System.out.println("Veículo adicionado ao cliente com ID: " + idCliente);

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar veículo ao cliente: " + e.getMessage());
        }
    }


    // Método para listar todos os clientes e seus veículos
    public List<ClienteModel> listarTodos() {
        String sqlClientes = "SELECT * FROM cliente";
        String sqlVeiculos = "SELECT * FROM veiculo WHERE id_cliente = ?";

        List<ClienteModel> clientes = new ArrayList<>();

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmtClientes = conn.prepareStatement(sqlClientes);
             ResultSet rsClientes = stmtClientes.executeQuery()) {

            while (rsClientes.next()) {
                String id = String.valueOf(rsClientes.getInt("id_cliente"));
                String nome = rsClientes.getString("nome");

                ClienteModel cliente = new ClienteModel(nome, id);

                // Carrega os veículos para o cliente atual
                try (PreparedStatement stmtVeiculos = conn.prepareStatement(sqlVeiculos)) {
                    stmtVeiculos.setInt(1, Integer.parseInt(id));
                    ResultSet rsVeiculos = stmtVeiculos.executeQuery();

                    while (rsVeiculos.next()) {
                        String placa = rsVeiculos.getString("placa");
                        cliente.adicionarVeiculo(new VeiculoModel(placa));
                    }
                }

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    // Método para buscar um cliente por ID
    public ClienteModel buscarPorId(String id) {
        String sqlCliente = "SELECT * FROM cliente WHERE id_cliente = ?";
        String sqlVeiculos = "SELECT * FROM veiculo WHERE id_cliente = ?";
        ClienteModel cliente = null;

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {

            stmtCliente.setInt(1, Integer.parseInt(id));
            ResultSet rsCliente = stmtCliente.executeQuery();

            if (rsCliente.next()) {
                String nome = rsCliente.getString("nome");
                cliente = new ClienteModel(nome, id);

                // Carrega os veículos para o cliente
                try (PreparedStatement stmtVeiculos = conn.prepareStatement(sqlVeiculos)) {
                    stmtVeiculos.setInt(1, Integer.parseInt(id));
                    ResultSet rsVeiculos = stmtVeiculos.executeQuery();

                    while (rsVeiculos.next()) {
                        String placa = rsVeiculos.getString("placa");
                        cliente.adicionarVeiculo(new VeiculoModel(placa));
                    }
                }

                System.out.println("Cliente encontrado com ID: " + id);
            } else {
                System.out.println("Nenhum cliente encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }

        return cliente;
    }

    // Método para buscar um cliente por placa de veículo
    public ClienteModel buscarClientePorPlaca(String placa) {
        String sql = "SELECT c.* FROM cliente c JOIN veiculo v ON c.id_cliente = v.id_cliente WHERE v.placa = ?";
        ClienteModel cliente = null;

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = String.valueOf(rs.getInt("id_cliente"));
                String nome = rs.getString("nome");
                cliente = new ClienteModel(nome, id);

                // Carrega os veículos do cliente
                String sqlVeiculos = "SELECT * FROM veiculo WHERE id_cliente = ?";
                try (PreparedStatement stmtVeiculos = conn.prepareStatement(sqlVeiculos)) {
                    stmtVeiculos.setInt(1, Integer.parseInt(id));
                    ResultSet rsVeiculos = stmtVeiculos.executeQuery();

                    while (rsVeiculos.next()) {
                        String placaVeiculo = rsVeiculos.getString("placa");
                        cliente.adicionarVeiculo(new VeiculoModel(placaVeiculo));
                    }
                }

                System.out.println("Cliente encontrado pela placa: " + placa);
            } else {
                System.out.println("Nenhum cliente encontrado para a placa: " + placa);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por placa: " + e.getMessage());
        }

        return cliente;
    }

    public void removerCliente(String idCliente) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idCliente));
            stmt.executeUpdate();

            System.out.println("Cliente com ID " + idCliente + " removido.");

        } catch (SQLException e) {
            System.err.println("Erro ao remover cliente: " + e.getMessage());
        }
    }

    public List<TicketModel> listarTicketsDoCliente(String idCliente) {
        String sql = "SELECT * FROM ticket WHERE id_cliente = ?";
        List<TicketModel> tickets = new ArrayList<>();

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idCliente));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TicketModel ticket = new TicketModel(
                        rs.getInt("id_ticket"),
                        rs.getInt("id_estacionamento"),
                        rs.getString("id_vaga"),
                        rs.getInt("id_cliente"),
                        rs.getTimestamp("entrada").toLocalDateTime(),
                        rs.getTimestamp("saida") != null ? rs.getTimestamp("saida").toLocalDateTime() : null,
                        rs.getBigDecimal("custo"),
                        rs.getString("placa")
                );
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar tickets do cliente: " + e.getMessage());
            e.printStackTrace();
        }

        return tickets;
    }

}
