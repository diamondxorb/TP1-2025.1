package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AtendenteView extends JFrame {

    public AtendenteView() {
        setTitle("Sistema DETRAN - Principal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel lblTitulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnCadastrarAtendente = new JButton("Cadastrar Atendente");
        JButton btnAgendamento = new JButton("Novo Agendamento");
        JButton btnConsultaAtendentes = new JButton("Consultar Atendentes");
        JButton btnConsultaAgendamentos = new JButton("Consultar Agendamentos");
        JButton btnSair = new JButton("Sair");

        btnCadastrarAtendente.addActionListener(e -> new CadastroAtendenteView());
        btnAgendamento.addActionListener(e -> new NovoAgendamentoView());
        btnConsultaAtendentes.addActionListener(e -> new ConsultaAtendentesView());
        btnConsultaAgendamentos.addActionListener(e -> new ConsultaAgendamentosView());
        btnSair.addActionListener(this::voltarParaLogin);

        panel.add(lblTitulo);
        panel.add(btnCadastrarAtendente);
        panel.add(btnAgendamento);
        panel.add(btnConsultaAtendentes);
        panel.add(btnConsultaAgendamentos);
        panel.add(btnSair);

        add(panel);
    }

    private void voltarParaLogin(ActionEvent e) {
        this.dispose();
        EventQueue.invokeLater(() -> {
            try {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
