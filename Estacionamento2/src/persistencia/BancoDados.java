package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável pela conexão com o banco de dados utilizando Singleton.
 */
public class BancoDados {

    // Nome do banco de dados que será utilizado
    private static String banco = "dbMinicurso";

    // Usuário do PostgreSQL
    private static String usuario = "postgres";

    // Senha do usuário do PostgreSQL
    private static String senha = "zak3";

    // URL do PostgreSQL incluindo o banco de dados
    private static String url = "jdbc:postgresql://localhost:5432/" + banco;

    // Atributo para garantir uma única instância (Singleton)
    private static BancoDados instancia = null;

    // Atributo para armazenar a conexão
    private static Connection conexao = null;

    // Construtor privado para evitar múltiplas instâncias (Singleton)
    private BancoDados() {
        // Construtor privado para evitar a criação de instâncias externas
    }

    // Método público para obter a instância única do banco de dados
    public static synchronized BancoDados getInstancia() {
        if (instancia == null) {
            instancia = new BancoDados();
            conectar();
        }
        return instancia;
    }

    // Método privado para realizar a conexão com o banco
    private static void conectar() {
        try {
            // Registrar o driver do PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Estabelecer a conexão
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão com o PostgreSQL estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            // Caso o driver não esteja disponível no classpath
            System.err.println("Driver do PostgreSQL não encontrado! Verifique o classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            // Caso ocorra algum erro ao conectar
            System.err.println("Erro ao conectar ao banco de dados:");
            System.err.println("URL: " + url);
            System.err.println("Usuário: " + usuario);
            e.printStackTrace();
        }
    }

    // Método público para obter a conexão ativa
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


    // Método público para desconectar o banco de dados
    public static void desconectar() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão com o banco de dados encerrada!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao encerrar a conexão com o banco de dados:");
            e.printStackTrace();
        }
    }
}
