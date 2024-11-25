package dao;

import persistencia.BancoDados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDAO {

    public double calcularArrecadacaoTotal(int idEstacionamento) {
        String sql = "SELECT COALESCE(SUM(custo), 0) AS total FROM ticket WHERE id_estacionamento = ?";
        double totalArrecadado = 0.0;

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEstacionamento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalArrecadado = rs.getDouble("total");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao calcular a arrecadação total: " + e.getMessage());
        }

        return totalArrecadado;
    }

    public double calcularValorMedioUtilizacao(int idEstacionamento) {
        String sql = "SELECT COALESCE(AVG(custo), 0) AS media FROM ticket WHERE id_estacionamento = ?";
        double valorMedio = 0.0;

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEstacionamento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    valorMedio = rs.getDouble("media");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao calcular o valor médio de utilização: " + e.getMessage());
        }

        return valorMedio;
    }

    public double calcularArrecadacaoMensal(int idEstacionamento, int mes, int ano) {
        String sql = "SELECT COALESCE(SUM(custo), 0) AS total " +
                "FROM ticket " +
                "WHERE id_estacionamento = ? " +
                "AND EXTRACT(MONTH FROM entrada) = ? " +
                "AND EXTRACT(YEAR FROM entrada) = ?";
        double totalArrecadado = 0.0;

        try (Connection connection = BancoDados.getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEstacionamento);
            stmt.setInt(2, mes);
            stmt.setInt(3, ano);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalArrecadado = rs.getDouble("total");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao calcular a arrecadação mensal: " + e.getMessage());
        }

        return totalArrecadado;
    }

}
