package dao;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistencia.BancoDados;

public class VagaDAO {

    public static boolean atualizarStatusVaga(String idVaga, boolean ocupada, int idEstacionamento) {
        String sql = "UPDATE vaga SET ocupada = ? WHERE id_vaga = ? AND id_estacionamento = ?";

        try (Connection connection = BancoDados.getInstancia().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {


            stmt.setBoolean(1, ocupada);
            stmt.setString(2, idVaga);
            stmt.setInt(3, idEstacionamento);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o status da vaga: " + e.getMessage());
            return false;
        }
    }

}
