/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danilo
 */
public class BancoDados {

    // Nome do banco de dados que será utilizado
    private static String banco = "dbMinicurso";

    // Usuário do PostgreSQL
    private static String usuario = "postgres";

    // Senha do usuário do PostgreSQL (alterada para "zak3")
    private static String senha = "zak3";

    // URL do PostgreSQL incluindo o banco de dados
    private static String url = "jdbc:postgresql://localhost:5432/" + banco;

    // Atributo para garantir uma única conexão (Singleton)
    private static BancoDados instancia = null;

    // Atributo para armazenar a conexão
    private static Connection conexao = null;

    // Construtor privado para evitar múltiplas instâncias (Singleton)
    private BancoDados() {

    }

    // Método público para obter a instância única do banco de dados
    public static BancoDados getInstancia() {

        if (instancia == null) {
            // Se ainda não houver instância, cria uma nova
            instancia = new BancoDados();
            // Estabelece a conexão com o banco de dados
            conectar();
        }
        return instancia;
    }

    // Método privado para realizar a conexão com o banco
    private static void conectar() {
        try {
            // Tenta estabelecer a conexão usando o driver do PostgreSQL
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão com o PostgreSQL estabelecida com sucesso!");
        } catch (SQLException ex) {
            // Loga qualquer erro que ocorrer
            System.err.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }
    }

    // Método público para obter a conexão ativa
    public static Connection getConexao() {
        return conexao;
    }

    // Método público para desconectar o banco de dados
    public void desconectar() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão com o banco de dados encerrada!");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao encerrar a conexão com o banco: " + ex.getMessage());
        }
    }
}
