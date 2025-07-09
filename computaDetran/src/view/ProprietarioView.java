package view;

import model.Proprietario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import util.EstiloUtil;
import util.FundoGradienteUtil;

public class ProprietarioView extends JFrame {
    private Proprietario proprietario;
    private DefaultTableModel tabelaModel;

    public ProprietarioView(Proprietario proprietario) {
        this.proprietario = proprietario;

        setTitle("Área do Proprietário - " + proprietario.getNome());
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Configuração de estilo
        setContentPane(new FundoGradienteUtil());
        setLayout(new BorderLayout());
        EstiloUtil.aplicarEstilo(this);

        initUI();
        try {
            atualizarLista();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Bem-vindo, Proprietário!", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Proprietário: " + proprietario.getNome() + " | CPF: " + proprietario.getCpf(), JLabel.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblSubtitle);

        // Tabela
        String[] columns = {"Local", "Status", "Motivo", "Veículo"};
        tabelaModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tabelaModel);
        table.setRowHeight(25);
        table.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Solicitações Recentes"));

        //Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton btnSolicitar = new JButton("Nova Solicitação");
        btnSolicitar.setPreferredSize(new Dimension(180, 40));
        btnSolicitar.addActionListener(e -> {
            Proprietario proprietario = new Proprietario();
            new SolicitacaoView(proprietario).setVisible(true);
            try {
                atualizarLista();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton btnLaudos = new JButton("Ver Laudos");
        btnLaudos.setPreferredSize(new Dimension(180, 40));
        btnLaudos.addActionListener(e -> {
            new LaudoView(proprietario).setVisible(true);
            try {
                atualizarLista();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton btnSair = new JButton("Sair");
        btnSair.setPreferredSize(new Dimension(180, 40));
        btnSair.addActionListener(this::voltarParaLogin);

        buttonPanel.add(btnSolicitar);
        buttonPanel.add(btnLaudos);
        buttonPanel.add(btnSair);

        //Layout final
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
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

    private void atualizarLista() throws SQLException {
        tabelaModel.setRowCount(0);

        tabelaModel.addRow(new Object[]{"1", "09-09-2009", "23:59", "Concluído", "TUY9F60"});
        tabelaModel.addRow(new Object[]{"2", "08-07-2025", "09:09", "Pendente", "FGY9M99"});
        tabelaModel.addRow(new Object[]{"3", "10-10-2023", "10:10", "Pendente", "TPQ9P67"});
        tabelaModel.addRow(new Object[]{"4", "17-08-2024", "17:04", "Concluído", "MNO0P99"});

        /*
        A parte do Proprietário não foi tão bem implementada e essa parte de conexão não funciona

        for (Agendamento ag : AgendamentoController.listarAgendamentos()) {
            if (ag.getVeiculo().getProprietario().getCpf().equals(proprietario.getCpf())) {
                tabelaModel.addRow(new Object[]{
                        ag.getId(),
                        ag.getData(),
                        ag.getHorario(),
                        ag.getStatus(),
                        ag.getVeiculo().getPlaca()
                });
            }
        }
         */
    }
}