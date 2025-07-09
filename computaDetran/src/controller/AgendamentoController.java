package controller;

import model.Proprietario;
import model.Agendamento;
import model.Atendente;
import model.Vistoriador;
import model.Laudo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Classe que gerencia todas as relações entre Agendamento e o banco de dados
public class AgendamentoController {

    //Função que serve para salvar um agendamento no banco de dados, utilizado pelo Atendente
    public static void salvarAgendamento(Agendamento ag) throws SQLException {
        String sql = "INSERT INTO agendamento (data, horario, motivoAgendamento, id_atendente, id_vistoriador) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ag.getData());
            stmt.setString(2, ag.getHorario());
            stmt.setString(3, ag.getMotivoAgendamento());
            stmt.setInt(4, ag.getAtendente().getId());
            stmt.setObject(5, ag.getVistoriador() != null ? ag.getVistoriador().getId() : null, Types.INTEGER);

            stmt.executeUpdate();
        }
    }

    //Nova função para criar agendamento pelo proprietário
    public static boolean criarAgendamento(String data, String horario, String motivo, String placa, String modelo, Proprietario proprietario) {
        String sql = "INSERT INTO agendamento (data, horario, motivoAgendamento, status) VALUES (?, ?, ?, 'Pendente')";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, data);
            stmt.setString(2, horario);
            stmt.setString(3, motivo);
            stmt.executeUpdate();

            //Obtém o ID do agendamento criado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idAgendamento = rs.getInt(1);
                    //Associa o veículo (e cria se não existir)
                    associarVeiculo(conn, idAgendamento, placa, modelo, proprietario);
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Função que associa o veículo ao agendamento
    private static void associarVeiculo(Connection conn, int idAgendamento, String placa, String modelo, Proprietario proprietario) throws SQLException {
        //Verifica se veículo existe
        String sqlCheck = "SELECT id FROM veiculo WHERE placa = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            int idVeiculo;
            if (rs.next()) {
                idVeiculo = rs.getInt("id");
            } else {
                //Cria novo veículo
                String sqlInsert = "INSERT INTO veiculo (placa, modelo, id_proprietario) VALUES (?, ?, ?)";
                try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    stmtInsert.setString(1, placa);
                    stmtInsert.setString(2, modelo);
                    stmtInsert.setInt(3, proprietario.getId());
                    stmtInsert.executeUpdate();

                    try (ResultSet rsInsert = stmtInsert.getGeneratedKeys()) {
                        if (rsInsert.next()) {
                            idVeiculo = rsInsert.getInt(1);
                        } else {
                            throw new SQLException("Falha ao obter ID do veículo");
                        }
                    }
                }
            }

            //Associa veículo ao agendamento
            String sqlUpdate = "UPDATE agendamento SET id_veiculo = ? WHERE id = ?";
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setInt(1, idVeiculo);
                stmtUpdate.setInt(2, idAgendamento);
                stmtUpdate.executeUpdate();
            }
        }
    }

    //Função para atendente aprovar agendamento
    public static boolean aprovarAgendamento(int idAgendamento, int idVistoriador) throws SQLException {
        String sql = "UPDATE agendamento SET status = 'Aprovado', id_vistoriador = ? WHERE id = ? AND status = 'Pendente'";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVistoriador);
            stmt.setInt(2, idAgendamento);
            return stmt.executeUpdate() > 0;
        }
    }

    //Função para atendente negar agendamento
    public static boolean negarAgendamento(int idAgendamento, String motivo) throws SQLException {
        String sql = "UPDATE agendamento SET status = 'Negado', motivo_negacao = ? WHERE id = ? AND status = 'Pendente'";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, motivo);
            stmt.setInt(2, idAgendamento);
            return stmt.executeUpdate() > 0;
        }
    }

    //Função para vistoriador emitir laudo
    public static boolean emitirLaudo(int idAgendamento, Laudo laudo) throws SQLException {
        Connection conn = Conexao.getConnection();
        try {
            conn.setAutoCommit(false);
            //Insere o laudo
            String sqlLaudo = "INSERT INTO laudo (status, motivo, dataEmissao, id_vistoriador, id_agendamento) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlLaudo, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, laudo.getStatus());
                stmt.setString(2, laudo.getMotivo());
                stmt.setTimestamp(3, new java.sql.Timestamp(laudo.getDataEmissao().getTime()));
                stmt.setInt(4, laudo.getVistoriador().getId());
                stmt.setInt(5, idAgendamento);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        laudo.setId(rs.getInt(1));
                    }
                }
            }

            //Atualiza o status do agendamento
            String sqlAgendamento = "UPDATE agendamento SET status = 'Concluído' WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlAgendamento)) {
                stmt.setInt(1, idAgendamento);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    //Função que lista os agendamentos para o atendente
    public static List<Agendamento> listarAgendamentos() throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = """
            SELECT a.id, a.data, a.horario, a.motivoAgendamento,
                   p.id AS atendente_id, p.nome AS atendente_nome, p.cpf AS atendente_cpf,
                   p.data_nascimento AS atendente_dn, p.endereco AS atendente_end, p.email AS atendente_email,
                   p.celular AS atendente_cel, p.numeroIdentificacao, p.senha,
                   v.id AS vistoriador_id, v.nome AS vistoriador_nome
            FROM agendamento a
            JOIN pessoa p ON a.id_atendente = p.id
            LEFT JOIN pessoa v ON a.id_vistoriador = v.id
            WHERE p.tipo = 'Atendente'
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Atendente atendente = new Atendente(
                        rs.getInt("atendente_id"),
                        rs.getString("atendente_nome"),
                        rs.getString("atendente_cpf"),
                        rs.getString("atendente_dn"),
                        rs.getString("atendente_end"),
                        rs.getString("atendente_email"),
                        rs.getString("atendente_cel"),
                        rs.getInt("numeroIdentificacao"),
                        rs.getString("senha")
                );

                Vistoriador vistoriador = null;
                if (rs.getInt("vistoriador_id") != 0) {
                    vistoriador = new Vistoriador(
                            rs.getInt("vistoriador_id"),
                            rs.getString("vistoriador_nome"),
                            null, null, null, null, null, 0, null, null
                    );
                }

                Agendamento ag = new Agendamento(
                        rs.getInt("id"),
                        rs.getString("data"),
                        rs.getString("horario"),
                        rs.getString("motivoAgendamento"),
                        atendente,
                        vistoriador
                );

                lista.add(ag);
            }
        }
        return lista;
    }

    //Função que retorna uma lista dos agendamentos de acordo com um vistoriador em específico, usado na tela do Vistoriador
    public static List<Agendamento> listarAgendamentosPorVistoriador(int idVistoriador) throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = """
        SELECT a.id, a.data, a.horario, a.motivoAgendamento,
               p.id AS atendente_id, p.nome AS atendente_nome, p.cpf AS atendente_cpf,
               p.data_nascimento AS atendente_dn, p.endereco AS atendente_end,
               p.email AS atendente_email, p.celular AS atendente_cel, p.numeroIdentificacao AS atendente_num,
               p.senha AS atendente_senha, v.id AS vistoriador_id, v.nome AS vistoriador_nome,
               v.cpf AS vistoriador_cpf, v.data_nascimento AS vistoriador_dn, v.endereco AS vistoriador_end,
               v.email AS vistoriador_email, v.celular AS vistoriador_cel, v.numeroIdentificacao AS vistoriador_num,
               v.senha AS vistoriador_senha
        FROM agendamento a
        JOIN pessoa p ON a.id_atendente = p.id
        JOIN pessoa v ON a.id_vistoriador = v.id
        WHERE a.id_vistoriador = ?
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVistoriador);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Atendente atendente = new Atendente(
                            rs.getInt("atendente_id"),
                            rs.getString("atendente_nome"),
                            rs.getString("atendente_cpf"),
                            rs.getString("atendente_dn"),
                            rs.getString("atendente_end"),
                            rs.getString("atendente_email"),
                            rs.getString("atendente_cel"),
                            rs.getInt("atendente_num"),
                            rs.getString("atendente_senha"));

                    Vistoriador vistoriador = new Vistoriador(
                            rs.getInt("vistoriador_id"),
                            rs.getString("vistoriador_nome"),
                            rs.getString("vistoriador_cpf"),
                            rs.getString("vistoriador_dn"),
                            rs.getString("vistoriador_end"),
                            rs.getString("vistoriador_email"),
                            rs.getString("vistoriador_cel"),
                            rs.getInt("vistoriador_num"),
                            rs.getString("vistoriador_senha"),
                            "Vistoriador");

                    Agendamento ag = new Agendamento(
                            rs.getInt("id"),
                            rs.getString("data"),
                            rs.getString("horario"),
                            rs.getString("motivoAgendamento"),
                            atendente,
                            vistoriador);

                    lista.add(ag);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar agendamentos:" + e.getMessage());
            e.printStackTrace();
            //Retorna lista vazia em caso de erro
            return Collections.emptyList();
        }
        return lista;
    }

    //Função para atualizar os dados de um agendamento
    public static void updateAgendamento(Agendamento ag) throws SQLException {
        String sql = "UPDATE agendamento SET data=?, horario=?, motivoAgendamento=?, id_atendente=?, id_vistoriador=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ag.getData());
            stmt.setString(2, ag.getHorario());
            stmt.setString(3, ag.getMotivoAgendamento());
            stmt.setInt(4, ag.getAtendente().getId());
            stmt.setObject(5, ag.getVistoriador() != null ? ag.getVistoriador().getId() : null, Types.INTEGER);
            stmt.setInt(6, ag.getId());

            stmt.executeUpdate();
        }
    }

    //Função para apagar um agendamento
    public static void deleteAgendamento(int id) throws SQLException {
        String sql = "DELETE FROM agendamento WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
