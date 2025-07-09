package view;

import controller.PessoaController;
import model.Atendente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ConsultaAtendentesView extends JFrame {

    private JTable tabela;
    private JTextField txtPesquisa;
    private JComboBox<String> cmbCampo;

    public ConsultaAtendentesView() {
        setTitle("Consulta de Atendentes");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Topo - pesquisa
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmbCampo = new JComboBox<>(new String[]{"nome", "cpf", "numeroIdentificacao"});
        txtPesquisa = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAtualizar = new JButton("Atualizar");

        topPanel.add(new JLabel("Campo:"));
        topPanel.add(cmbCampo);
        topPanel.add(new JLabel("Valor:"));
        topPanel.add(txtPesquisa);
        topPanel.add(btnBuscar);
        topPanel.add(btnAtualizar);

        // Tabela
        tabela = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabela);

        // Rodapé - ações
        JPanel bottomPanel = new JPanel();
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnExcluir = new JButton("Excluir Selecionado");

        bottomPanel.add(btnEditar);
        bottomPanel.add(btnExcluir);

        btnBuscar.addActionListener(e -> buscar());
        btnAtualizar.addActionListener(e -> carregarTodos());
        btnExcluir.addActionListener(e -> excluirSelecionado());
        btnEditar.addActionListener(e -> editarSelecionado());

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        carregarTodos();
    }

    private void carregarTodos() {
        try {
            List<Atendente> lista = PessoaController.listarAtendentes();
            atualizarTabela(lista);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage());
        }
    }

    private void buscar() {
        String campo = cmbCampo.getSelectedItem().toString();
        String valor = txtPesquisa.getText().trim();
        try {
            List<Atendente> lista = PessoaController.buscarAtendentes(campo, valor);
            atualizarTabela(lista);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro na busca: " + e.getMessage());
        }
    }

    private void atualizarTabela(List<Atendente> lista) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Nome", "CPF", "Número ID"});
        for (Atendente a : lista) {
            model.addRow(new Object[]{
                    a.getId(),
                    a.getNome(),
                    a.getCpf(),
                    a.getNumeroIdentificacao()
            });
        }
        tabela.setModel(model);
    }

    private void excluirSelecionado() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir!");
            return;
        }
        int id = (int) tabela.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PessoaController.excluirAtendente(id);
                carregarTodos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private void editarSelecionado() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!");
            return;
        }

        int id = (int) tabela.getValueAt(row, 0);
        String nome = tabela.getValueAt(row, 1).toString();
        String cpf = tabela.getValueAt(row, 2).toString();
        int numeroId = Integer.parseInt(tabela.getValueAt(row, 3).toString());

        Atendente a = new Atendente();
        a.setId(id);
        a.setNome(nome);
        a.setCpf(cpf);
        a.setNumeroIdentificacao(numeroId);

        new EditarAtendenteView(a);
    }
}
