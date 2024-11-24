package dao;

import model.*;
import persistencia.BancoDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstacionamentoDAO {

    public int salvarEstacionamento(EstacionamentoModel estacionamento) {
        String sql = "INSERT INTO estacionamento (nome, endereco, telefone) VALUES (?, ?, ?) RETURNING id_estacionamento";

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, estacionamento.getNome());
            stmt.setString(2, estacionamento.getEndereco());
            stmt.setString(3, estacionamento.getTelefone());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_estacionamento"); // Retorna o ID gerado
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar estacionamento: " + e.getMessage());
        }
        return 0; // Retorne 0 ou lance uma exceção se falhar
    }

    public List<EstacionamentoModel> listarEstacionamentos() {
        List<EstacionamentoModel> estacionamentos = new ArrayList<>();
        String sql = "SELECT id_estacionamento, nome, endereco, telefone FROM estacionamento";

        try (Connection connection = BancoDados.getConexao();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_estacionamento");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String telefone = rs.getString("telefone");

                EstacionamentoModel estacionamento = new EstacionamentoModel(id, nome, endereco, telefone);
                estacionamentos.add(estacionamento);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar estacionamentos: " + e.getMessage());
        }

        return estacionamentos;
    }

    public boolean removerEstacionamentoPorId(int id) {
        String sql = "DELETE FROM estacionamento WHERE id_estacionamento = ?";
        boolean removed = false;

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            removed = rowsDeleted > 0;

            if (removed) {
                System.out.println("Estacionamento com ID " + id + " removido com sucesso.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao remover estacionamento: " + e.getMessage());
        }

        return removed;
    }

    public void salvarVagas(List<VagaModel> vagas) {
        String sql = "INSERT INTO vaga (id_vaga, ocupada, id_estacionamento, tipo) VALUES (?, ?, ?, ?)";

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (VagaModel vaga : vagas) {
                stmt.setString(1, vaga.getId());
                stmt.setBoolean(2, vaga.isOcupada());
                stmt.setInt(3, vaga.getIdEstacionamento()); // Certifique-se de que este valor é válido
                stmt.setString(4, vaga.getTipo());
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar vagas: " + e.getMessage());
        }
    }

    public EstacionamentoModel buscarEstacionamentoPorId(int id) {
        String sql = "SELECT id_estacionamento, nome, endereco, telefone FROM estacionamento WHERE id_estacionamento = ?";
        EstacionamentoModel estacionamento = null;

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String endereco = rs.getString("endereco");
                    String telefone = rs.getString("telefone");

                    estacionamento = new EstacionamentoModel(id, nome, endereco, telefone);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar estacionamento: " + e.getMessage());
        }

        return estacionamento;
    }

    public List<VagaModel> listarVagasPorEstacionamento(int idEstacionamento) {
        List<VagaModel> vagas = new ArrayList<>();
        String sql = "SELECT id_vaga, tipo, ocupada FROM vaga WHERE id_estacionamento = ?";

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEstacionamento);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String idVaga = rs.getString("id_vaga");
                    String tipo = rs.getString("tipo");
                    boolean ocupada = rs.getBoolean("ocupada");

                    VagaModel vaga;
                    switch (tipo) {
                        case "PCD":
                            vaga = new VagaPCDModel(idVaga);
                            break;
                        case "VIP":
                            vaga = new VagaVIPModel(idVaga);
                            break;
                        default:
                            vaga = new VagaPadraoModel(idVaga);
                    }

                    vaga.setOcupada(ocupada);
                    vaga.setIdEstacionamento(idEstacionamento);
                    vagas.add(vaga);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vagas por estacionamento: " + e.getMessage());
        }

        return vagas;
    }

}
