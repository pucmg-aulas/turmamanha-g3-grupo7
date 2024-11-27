package dao;

import model.ClienteModel;
import model.TicketModel;
import model.VeiculoModel;
import persistencia.BancoDados;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {


    public void salvarCliente(ClienteModel cliente) {
        String sql = "INSERT INTO cliente (nome) VALUES (?) RETURNING id_cliente";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente.setId(String.valueOf(rs.getInt("id_cliente"))); // Define o ID gerado
            }

            System.out.println("Cliente salvo com ID: " + cliente.getId());
        } catch (SQLException e) {
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean existeIdCliente(String idCliente) {
        String sql = "SELECT 1 FROM cliente WHERE id_cliente = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idCliente));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência do cliente: " + e.getMessage());
            return false;
        }
    }

    public void adicionarVeiculoAoCliente(String idCliente, VeiculoModel veiculo) {
        if (veiculoJaExiste(veiculo.getPlaca())) {
            System.out.println("Veículo com a placa " + veiculo.getPlaca() + " já existe!");
            return;
        }

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

    public boolean veiculoJaExiste(String placa) {
        String sql = "SELECT 1 FROM veiculo WHERE placa = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência do veículo: " + e.getMessage());
            return false;
        }
    }

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

    public List<ClienteModel> obterRankingClientes(int mes, int ano) {
        String baseSql = """
        SELECT c.id_cliente, c.nome, SUM(t.custo) AS total_gasto
        FROM cliente c
        JOIN ticket t ON c.id_cliente = t.id_cliente
    """;

        StringBuilder sql = new StringBuilder(baseSql);

        // Adicionar condições dinamicamente
        if (mes > 0 || ano > 0) {
            sql.append(" WHERE ");
            if (mes > 0) {
                sql.append("EXTRACT(MONTH FROM t.entrada) = ? ");
            }
            if (mes > 0 && ano > 0) {
                sql.append("AND ");
            }
            if (ano > 0) {
                sql.append("EXTRACT(YEAR FROM t.entrada) = ? ");
            }
        }

        sql.append("""
        GROUP BY c.id_cliente, c.nome
        ORDER BY total_gasto DESC
        LIMIT 5
    """);

        List<ClienteModel> ranking = new ArrayList<>();

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            // Passar os parâmetros para o PreparedStatement
            if (mes > 0) {
                stmt.setInt(paramIndex++, mes);
            }
            if (ano > 0) {
                stmt.setInt(paramIndex++, ano);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id_cliente"));
                String nome = rs.getString("nome");
                BigDecimal totalGasto = rs.getBigDecimal("total_gasto");

                ClienteModel cliente = new ClienteModel(nome, id);
                cliente.setTotalGasto(totalGasto);
                ranking.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter ranking de clientes: " + e.getMessage());
            e.printStackTrace();
        }

        return ranking;
    }

    public List<TicketModel> filtrarTicketsSimples(String idCliente, String entrada, String saida) {
        String baseSql = "SELECT * FROM ticket WHERE id_cliente = ?";
        StringBuilder sql = new StringBuilder(baseSql);

        // Adicionar condições dinamicamente
        if (!entrada.isEmpty()) {
            sql.append(" AND DATE(entrada) = ?");
        }
        if (!saida.isEmpty()) {
            sql.append(" AND DATE(saida) = ?");
        }

        List<TicketModel> tickets = new ArrayList<>();

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            stmt.setInt(1, Integer.parseInt(idCliente));
            int paramIndex = 2;

            if (!entrada.isEmpty()) {
                stmt.setDate(paramIndex++, Date.valueOf(entrada));
            }
            if (!saida.isEmpty()) {
                stmt.setDate(paramIndex, Date.valueOf(saida));
            }

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
            System.err.println("Erro ao filtrar tickets: " + e.getMessage());
            e.printStackTrace();
        }

        return tickets;
    }

    public List<ClienteModel> buscarPorNomeOuPlaca(String termo) {
        String sql = """
        SELECT DISTINCT c.id_cliente, c.nome, v.placa
        FROM cliente c
        LEFT JOIN veiculo v ON c.id_cliente = v.id_cliente
        WHERE LOWER(c.nome) LIKE ? OR LOWER(v.placa) LIKE ?
        """;

        List<ClienteModel> clientes = new ArrayList<>();

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String termoBusca = "%" + termo.toLowerCase() + "%";
            stmt.setString(1, termoBusca);
            stmt.setString(2, termoBusca);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = String.valueOf(rs.getInt("id_cliente"));
                    String nome = rs.getString("nome");
                    String placa = rs.getString("placa");

                    // Verifica se o cliente já está na lista
                    ClienteModel cliente = clientes.stream()
                            .filter(c -> c.getId().equals(id))
                            .findFirst()
                            .orElse(null);

                    if (cliente == null) {
                        cliente = new ClienteModel(nome, id);
                        clientes.add(cliente);
                    }

                    // Adiciona o veículo ao cliente, se houver
                    if (placa != null) {
                        cliente.adicionarVeiculo(new VeiculoModel(placa));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar clientes por nome ou placa: " + e.getMessage());
        }

        return clientes;
    }



}
