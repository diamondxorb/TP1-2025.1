package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Classe que inicializa o banco de dados, cria tabelas para certos tipos de dados
public class Conexao {

    private static final String URL = "jdbc:sqlite:computaDetran.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBanco() {
        //Tabela de pessoas
        String sqlPessoa = """
            CREATE TABLE IF NOT EXISTS pessoa (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                cpf TEXT NOT NULL,
                data_nascimento TEXT,
                endereco TEXT,
                email TEXT,
                celular TEXT,
                tipo TEXT NOT NULL,
                numeroIdentificacao INTEGER,
                senha TEXT
            );
        """;

        //Tabela de agendamentos
        String sqlAgendamento = """
            CREATE TABLE IF NOT EXISTS agendamento (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                data TEXT NOT NULL,
                horario TEXT NOT NULL,
                motivoAgendamento TEXT,
                id_atendente INTEGER NOT NULL,
                id_vistoriador INTEGER,
                FOREIGN KEY (id_atendente) REFERENCES pessoa(id),
                FOREIGN KEY (id_vistoriador) REFERENCES pessoa(id)
            );
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlPessoa);
            stmt.execute(sqlAgendamento);
            System.out.println("\nTabelas criadas/verificadas com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
