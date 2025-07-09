package view;

import controller.PessoaController;
import model.Atendente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditarAtendenteView extends JFrame {

    private JTextField txtNome, txtCpf, txtNumeroId;
    private JButton btnSalvar;

    private Atendente atendente;

    public EditarAtendenteView(Atendente a) {
        this.atendente = a;

        setTitle("Editar Atendente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        txtNome = new JTextField(a.getNome());
        txtCpf = new JTextField(a.getCpf());
        txtNumeroId = new JTextField(String.valueOf(a.getNumeroIdentificacao()));

        panel.add(new JLabel("Nome:")); panel.add(txtNome);
        panel.add(new JLabel("CPF:")); panel.add(txtCpf);
        panel.add(new JLabel("Número ID:")); panel.add(txtNumeroId);

        btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setBackground(new Color(255, 204, 0));

        btnSalvar.addActionListener(e -> {
            try {
                atendente.setNome(txtNome.getText());
                atendente.setCpf(txtCpf.getText());
                atendente.setNumeroIdentificacao(Integer.parseInt(txtNumeroId.getText()));
                PessoaController.atualizarAtendente(atendente);
                JOptionPane.showMessageDialog(this, "Atendente atualizado!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("")); panel.add(btnSalvar);

        add(panel);
        setVisible(true);
    }
}
