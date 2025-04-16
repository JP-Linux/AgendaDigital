package agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Paulo Santos
 */

public final class BancoDeDados {

    private final String URL = "jdbc:mysql://localhost:3306/teste";
    private final String USUARIO = "root";
    private final String SENHA = System.getenv("DB_PASSWORD");
    private Connection conexao;

    public BancoDeDados() {
        try {
            this.conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão bem-sucedida!");
            criarTabela();  // Cria tabela se não existir
        } catch (SQLException ex) {
            System.err.println("Falha ao conectar: " + ex.getMessage());
        }
    }
    
    public boolean estaConectado() {
        try {
            return conexao != null && 
                   !conexao.isClosed() && 
                   conexao.isValid(2); // Testa a conexão com timeout de 2 segundos
        } catch (SQLException ex) {
            return false;
        }
    }

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS Agenda ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nome VARCHAR(100) NOT NULL,"
                + "telefone VARCHAR(20) NOT NULL,"
                + "email VARCHAR(100))";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public void adicionarContato(String nome, String telefone, String email) {
        // Validação básica
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: Nome é obrigatório!");
            return;
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            System.out.println("Erro: Telefone é obrigatório!");
            return;
        }

        // Validação opcional de email
        if (!email.isEmpty() && !email.contains("@")) {
            System.out.println("Erro: Email inválido!");
            return;
        }

        // Query com tratamento para email vazio
        String sql = "INSERT INTO Agenda (nome, telefone, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, email.isEmpty() ? null : email); // Armazena NULL se vazio
            stmt.executeUpdate();
            System.out.println("✅ Contato adicionado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar: " + e.getMessage());
        }
    }

    public DefaultTableModel buscarContato(String nome) {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Telefone", "Email"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };

        String sql;
        boolean filtrarPorNome = nome != null && !nome.trim().isEmpty();

        if (filtrarPorNome) {
            sql = "SELECT * FROM Agenda WHERE nome LIKE ?";
        } else {
            sql = "SELECT * FROM Agenda";
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (filtrarPorNome) {
                stmt.setString(1, "%" + nome.trim() + "%");
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email")
                });
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar: " + e.getMessage());
        }

        return model;
    }

    // Mantenha o método sobrecarregado para busca sem filtro
    public DefaultTableModel buscarContato() {
        return buscarContato("");
    }

    public void deletarContato(int id) throws SQLException {
        String sql = "DELETE FROM Agenda WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhum contato encontrado com o ID " + id);
            }
        }
    }

    public void finalizaConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}