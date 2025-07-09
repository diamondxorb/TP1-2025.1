package view;

import controller.UsuarioController;
import util.EstiloUtil;
import util.FundoGradienteUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

//Classe da tela de boas-vindas do Vistoriador
public class VistoriadorView extends JFrame {
    public VistoriadorView(String user) {
        UsuarioController uc = new UsuarioController();
        String nome = uc.retornaNome(user);
        setTitle("Área do Vistoriador - " + nome);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Configuração das cores e estilo
        setContentPane(new FundoGradienteUtil());
        setLayout(new BorderLayout());
        EstiloUtil.aplicarEstilo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JLabel lblBemVindo = new JLabel("Bem-vindo, Vistoriador " + nome, JLabel.CENTER);
        lblBemVindo.setFont(new Font("Comic Sans", Font.BOLD, 18));
        mainPanel.add(lblBemVindo, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton btnAgendamentos = new JButton("Meus Agendamentos");
        btnAgendamentos.addActionListener(e -> {
            //Fecha janela atual
            this.dispose();

            try {
                new VistoriadorAgendamentosView(user).setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonPanel.add(btnAgendamentos);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
