package view;

import controller.AgendamentoController;
import model.Agendamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ConsultaAgendamentosView extends JFrame {

    private JTable tabela;

    public ConsultaAgendamentosView() {
        setTitle("Consulta de Agendamentos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> carregarDados());

        tabela = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton btnExcluir = new JButton("Excluir Selecionado");
        btnExcluir.addActionListener(e -> excluirSelecionado());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnAtualizar);
        bottomPanel.add(btnExcluir);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        carregarDados();
        add(panel);
    }

    private void carregarDados() {
        try {
            List<Agendamento> lista = AgendamentoController.listarAgendamentos();
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID", "Data", "Horário", "Motivo", "Atendente", "Vistoriador"});
            for (Agendamento ag : lista) {
                model.addRow(new Object[]{
                        ag.getId(),
                        ag.getData(),
                        ag.getHorario(),
                        ag.getMotivoAgendamento(),
                        ag.getAtendente().getNome(),
                        ag.getVistoriador() != null ? ag.getVistoriador().getNome() : "N/A"
                });
            }
            tabela.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void excluirSelecionado() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha!");
            return;
        }
        int id = (int) tabela.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                AgendamentoController.deleteAgendamento(id);
                carregarDados();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }
}
