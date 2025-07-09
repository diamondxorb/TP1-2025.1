package view;

import controller.AgendamentoController;
import controller.PessoaController;
import model.Agendamento;
import model.Atendente;
import model.Vistoriador;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class NovoAgendamentoView extends JFrame {

    private JComboBox<String> comboAtendentes;
    private JComboBox<String> comboVistoriadores;
    private JTextField txtData, txtHorario, txtMotivo;
    private List<Atendente> listaAtendentes;
    private List<Vistoriador> listaVistoriadores;

    public NovoAgendamentoView() {
        setTitle("Novo Agendamento");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtData = new JTextField();
        txtHorario = new JTextField();
        txtMotivo = new JTextField();

        comboAtendentes = new JComboBox<>();
        comboVistoriadores = new JComboBox<>();

        panel.add(new JLabel("Data (YYYY-MM-DD):"));
        panel.add(txtData);

        panel.add(new JLabel("Horário (HH:MM):"));
        panel.add(txtHorario);

        panel.add(new JLabel("Motivo Agendamento:"));
        panel.add(txtMotivo);

        panel.add(new JLabel("Atendente:"));
        panel.add(comboAtendentes);

        panel.add(new JLabel("Vistoriador:"));
        panel.add(comboVistoriadores);

        JButton btnAgendar = new JButton("Agendar");
        btnAgendar.addActionListener(e -> salvarAgendamento());

        panel.add(new JLabel(""));
        panel.add(btnAgendar);

        add(panel);
        carregarAtendentes();
        carregarVistoriadores();
    }

    private void carregarAtendentes() {
        try {
            listaAtendentes = PessoaController.listarAtendentes();
            comboAtendentes.removeAllItems();
            for (Atendente a : listaAtendentes) {
                comboAtendentes.addItem(a.getNome() + " (ID: " + a.getId() + ")");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar atendentes: " + e.getMessage());
        }
    }

    private void carregarVistoriadores() {
        try {
            listaVistoriadores = PessoaController.listarVistoriadores();
            comboVistoriadores.removeAllItems();
            for (Vistoriador v : listaVistoriadores) {
                comboVistoriadores.addItem(v.getNome() + " (ID: " + v.getId() + ")");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vistoriadores: " + e.getMessage());
        }
    }

    private void salvarAgendamento() {
        try {
            String data = txtData.getText();
            String horario = txtHorario.getText();
            String motivo = txtMotivo.getText();

            if (data.isEmpty() || horario.isEmpty() || motivo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            Atendente atendente = listaAtendentes.get(comboAtendentes.getSelectedIndex());
            Vistoriador vistoriador = listaVistoriadores.get(comboVistoriadores.getSelectedIndex());

            Agendamento ag = new Agendamento(data, horario, motivo, atendente, vistoriador);

            AgendamentoController.salvarAgendamento(ag);
            JOptionPane.showMessageDialog(this, "Agendamento salvo com sucesso!");
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
}
