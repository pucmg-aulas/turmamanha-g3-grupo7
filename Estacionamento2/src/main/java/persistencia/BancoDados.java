package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável pela conexão com o banco de dados utilizando Singleton.
 */
public class BancoDados {

    // Configuração do banco na nuvem
    private static String banco = "railway"; // Nome do banco
    private static String usuario = "postgres"; // Usuário
    private static String senha = "rHYZrSgwQjIUvPjvrQOEUEkQFwyhmBwd"; // Insira a senha correta aqui
    private static String host = "junction.proxy.rlwy.net"; // Host fornecido
    private static String port = "40694"; // Porta fornecida
    private static String url = "jdbc:postgresql://" + host + ":" + port + "/" + banco; // URL final

    // Atributo Singleton
    private static BancoDados instancia = null;

    // Conexão
    private static Connection conexao = null;

    // Construtor privado
    private BancoDados() {}

    // Método público para obter a instância única
    public static synchronized BancoDados getInstancia() {
        if (instancia == null) {
            instancia = new BancoDados();
            conectar();
        }
        return instancia;
    }

    // Método privado para realizar a conexão
    private static void conectar() {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão com o banco PostgreSQL na nuvem foi estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados:");
            System.err.println("URL: " + url);
            System.err.println("Usuário: " + usuario);
            e.printStackTrace();
        }
    }

    // Método público para obter a conexão
    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                System.out.println("Conexão está nula ou fechada. Tentando reconectar...");
                conectar();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar o estado da conexão: " + e.getMessage());
        }
        return conexao;
    }

    // Método para desconectar
    public static void desconectar() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão encerrada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao encerrar a conexão com o banco de dados:");
            e.printStackTrace();
        }
    }
}
