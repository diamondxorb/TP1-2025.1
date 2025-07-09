package view;

import controller.PessoaController;
import model.Atendente;
import util.HashUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CadastroAtendenteView extends JFrame {

    private JTextField txtNome, txtCpf, txtNascimento, txtEndereco, txtEmail, txtCelular, txtNumeroId;
    private JPasswordField txtSenha;
    private JButton btnSalvar;

    public CadastroAtendenteView() {
        setTitle("Cadastro de Atendente");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Nome:", "CPF:", "Nascimento (YYYY-MM-DD):", "Endereço:", "E-mail:", "Celular:", "Número Identificação:", "Senha:"};
        JTextField[] fields = new JTextField[labels.length];
        txtNome = new JTextField(); txtCpf = new JTextField(); txtNascimento = new JTextField();
        txtEndereco = new JTextField(); txtEmail = new JTextField(); txtCelular = new JTextField();
        txtNumeroId = new JTextField(); txtSenha = new JPasswordField();
        fields[0]=txtNome; fields[1]=txtCpf; fields[2]=txtNascimento; fields[3]=txtEndereco;
        fields[4]=txtEmail; fields[5]=txtCelular; fields[6]=txtNumeroId;

        gbc.gridx = 0; gbc.gridy = 0;
        for (int i = 0; i < labels.length; i++) {
            mainPanel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            mainPanel.add(i < 7 ? fields[i] : txtSenha, gbc);
            gbc.gridx = 0; gbc.gridy++;
        }

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(255, 204, 0));
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> {
            try {
                int numeroId = Integer.parseInt(txtNumeroId.getText());
                String senhaHash = HashUtil.sha256(new String(txtSenha.getPassword()));
                Atendente a = new Atendente(
                        txtNome.getText(), txtCpf.getText(), txtNascimento.getText(),
                        txtEndereco.getText(), txtEmail.getText(), txtCelular.getText(),
                        numeroId, senhaHash
                );
                PessoaController.salvarAtendente(a);
                JOptionPane.showMessageDialog(this, "Atendente salvo com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
