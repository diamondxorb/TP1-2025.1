package view;

import controller.LaudoController;
import model.Laudo;
import model.Proprietario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import util.EstiloUtil;
import util.FundoGradienteUtil;

public class LaudoView extends JFrame {
    public LaudoView(Proprietario proprietario) {
        setTitle("Meus Laudos - " + proprietario.getNome());
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //Configuração de estilo
        setContentPane(new FundoGradienteUtil());
        setLayout(new BorderLayout());
        EstiloUtil.aplicarEstilo(this);

        initUI(proprietario);
    }

    private void initUI(Proprietario proprietario) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //Cabeçalho
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel lblTitle = new JLabel("Laudos do Veículo", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel lblSubtitle = new JLabel("Proprietário: " + proprietario.getNome() + " | CPF: " + proprietario.getCpf(), JLabel.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        headerPanel.add(lblTitle, BorderLayout.NORTH);
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);

        //Tabela de laudos
        String[] columns = {"Data", "Placa", "Modelo", "Status", "Vistoriador"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setBackground(Color.WHITE);

        model.addRow(new Object[]{"09/09/2009 23:59", "TUY9F60", "Jeep Renegade", "Concluído", "julia paulo amorim"});
        model.addRow(new Object[]{"08/07/2024 12:34", "PQM7Y88", "Renault Kwid", "Concluído", "julia paulo amorim"});

        /*
        A parte do Proprietário não foi tão bem implementada e essa parte de conexão não funciona

        LaudoController.listarPorProprietario(proprietario.getCpf()).forEach(laudo -> {
            Object[] row = {
                    laudo.getDataEmissaoFormatada(),
                    laudo.getVeiculo() != null ? laudo.getVeiculo().getPlaca() : "N/A",
                    laudo.getVeiculo() != null ? laudo.getVeiculo().getModelo() : "N/A",
                    laudo.getStatus(),
                    laudo.getVistoriador() != null ? laudo.getVistoriador().getNome() : "N/A"
            };
            model.addRow(row);
        });
         */

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Laudos Emitidos"));

        //Painel de detalhes
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setOpaque(false);
        detailPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Laudo"));
        JTextArea taDetails = new JTextArea(8, 60);
        taDetails.setEditable(false);
        detailPanel.add(new JScrollPane(taDetails), BorderLayout.CENTER);

        //Listener para seleção na tabela
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Laudo laudo = LaudoController.listarPorProprietario(proprietario.getCpf()).get(row);
                taDetails.setText(formatLaudoDetails(laudo));
            }
        });

        //Layout principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(detailPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private String formatLaudoDetails(Laudo laudo) {
        return String.format(
                "DATA: %s\n" +
                        "VEÍCULO: %s (%s)\n" +
                        "STATUS: %s\n" +
                        "VISTORIADOR: %s\n" +
                        "MOTIVO: %s",
                laudo.getDataEmissaoFormatada(),
                laudo.getAgendamento().getVeiculo().getModelo(),
                laudo.getAgendamento().getVeiculo().getPlaca(),
                laudo.getStatus(),
                laudo.getVistoriador().getNome(),
                laudo.getMotivo()
        );
    }
}