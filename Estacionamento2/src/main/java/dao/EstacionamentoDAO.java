package dao;

import model.EstacionamentoModel;
import persistencia.BancoDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstacionamentoDAO {

    public void salvarEstacionamento(EstacionamentoModel estacionamento) {
        String sql = "INSERT INTO estacionamento (nome, endereco, telefone) VALUES (?, ?, ?)";

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, estacionamento.getNome());
            stmt.setString(2, estacionamento.getEndereco());
            stmt.setString(3, estacionamento.getTelefone());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Estacionamento salvo com sucesso: " + estacionamento.getNome());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar estacionamento: " + e.getMessage());
        }
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
}
