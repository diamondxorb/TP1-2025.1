package controller;

import model.Proprietario;
import model.Atendente;
import model.Vistoriador;
import util.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Classe intermediária entre a PessoaController e as telas de Login e Cadastro
//Implementa muitas funções em que a PessoaController, por ter mais acesso ao banco de dados, não tem tantos retornos
public class UsuarioController {
    //Função para cadastrar usuários no sistema, utiliza sobrecarga conforme a quantidade de parâmetros
    //cadastrarPessoa para Proprietário
    public boolean cadastrarPessoa(String nome, String cpf, String dataNasc, String endereco, String email, String celular, String senha) {
        try {
            if(usuarioExiste(cpf)) {
                return false;
            }
            Proprietario proprietario = new Proprietario(nome, cpf, dataNasc, endereco, email, celular, senha);
            PessoaController.salvarProprietario(proprietario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //cadastrarPessoa para Atendente
    public boolean cadastrarPessoa(String nome, String cpf, String dataNasc, String endereco, String email, String celular, String senha, int numeroIdentificacao) {
        try {
            if(usuarioExiste(cpf)) {
                return false;
            }
            Atendente atendente = new Atendente(nome, cpf, dataNasc, endereco, email, celular, numeroIdentificacao, senha);
            PessoaController.salvarAtendente(atendente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //cadastrarPessoa para Vistoriador
    public boolean cadastrarPessoa(String nome, String cpf, String dataNasc, String endereco, String email, String celular, String senha, int cadastroDetran, String assinatura) {
        try {
            if(usuarioExiste(cpf)) {
                return false;
            }
            Vistoriador vistoriador = new Vistoriador(nome, cpf, dataNasc, endereco, email, celular, cadastroDetran, senha, assinatura);
            PessoaController.salvarVistoriador(vistoriador);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Verifica se já existe uma conta com o mesmo cpf
    public boolean usuarioExiste(String cpf) {
        String sql = "SELECT 1 FROM pessoa WHERE cpf = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);

            try (ResultSet rs = pstmt.executeQuery()) {
                //Vai retornar true se o usuário existe no banco de dados
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar cadastro existente: " + e.getMessage());
            return false;
        }
    }

    //Verifica se o login está correto
    public boolean senhaCorreta(String cpf , String senha) {
        String sql = "SELECT 1 FROM pessoa WHERE cpf = ? AND senha = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String senhaHash = HashUtil.sha256(new String(senha));

            pstmt.setString(1, cpf);
            pstmt.setString(2, senha);

            try (ResultSet rs = pstmt.executeQuery()) {
                //Vai retornar true se o usuário existe com essa senha
                return rs.next();
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar senha: " + e.getMessage());
            return false;
        }
    }

    //Retorna o perfil do usuário, se é Atendente, Vistoriador ou Proprietário
    public String retornaPerfil(String cpf) {
        String sql = "SELECT tipo FROM pessoa WHERE cpf = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);

            try (ResultSet rs = pstmt.executeQuery()) {
                //Vai retornar a string com o perfil do usuário
                return rs.next() ? rs.getString("tipo") : null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter perfil do usuário: " + e.getMessage());
            return null;
        }
    }

    //Retorna o nome do usuário
    public String retornaNome(String cpf) {
        String sql = "SELECT nome FROM pessoa WHERE cpf = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);

            try (ResultSet rs = pstmt.executeQuery()) {
                //Vai retornar a string com o nome do usuário
                return rs.next() ? rs.getString("nome") : null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter nome do usuário: " + e.getMessage());
            return null;
        }
    }

    //Retorna o cpf do usuário a partir do nome
    public String retornaCpf(String nome) {
        String sql = "SELECT cpf FROM pessoa WHERE nome = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);

            try (ResultSet rs = pstmt.executeQuery()) {
                //Vai retornar a string com o nome do usuário
                return rs.next() ? rs.getString("cpf") : null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter cpf do usuário: " + e.getMessage());
            return null;
        }
    }

    //Retorna o ID a partir do cpf
    public int retornaId(String nome) {
        String sql = "SELECT id FROM pessoa WHERE nome = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);

            try (ResultSet rs = pstmt.executeQuery()) {
                //Vai retornar a string com o nome do usuário
                return rs.next() ? rs.getInt("id") : 0;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter nome do usuário: " + e.getMessage());
            return 0;
        }
    }
}
