package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

import model.Agendamento;
import model.Laudo;
import model.Vistoriador;
import util.EstiloUtil;
import util.FundoGradienteUtil;

public class EmitirLaudoView extends JFrame {
    private final Agendamento agendamento;
    private final String nome;
    private JRadioButton rbAprovado, rbNegado;
    private JTextArea taMotivo;

    public EmitirLaudoView(Agendamento agendamento, String nome) {
        this.agendamento = agendamento;
        this.nome = nome;

        setTitle("Emitir Laudo - Agendamento n° " + agendamento.getId());
        setSize(500, 400);
        setLocationRelativeTo(null);

        //Configuração das cores e estilo
        setContentPane(new FundoGradienteUtil());
        setLayout(new BorderLayout());
        EstiloUtil.aplicarEstilo(this);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Status do Laudo:"), gbc);

        // RadioButton para Aprovado ou Negado
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        ButtonGroup bgStatus = new ButtonGroup();

        rbAprovado = new JRadioButton("Aprovado");
        rbNegado = new JRadioButton("Negado");

        bgStatus.add(rbAprovado);
        bgStatus.add(rbNegado);

        statusPanel.add(rbAprovado);
        statusPanel.add(rbNegado);

        gbc.gridy = 1;
        formPanel.add(statusPanel, gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("Motivo:"), gbc);

        gbc.gridy = 3;
        taMotivo = new JTextArea(5, 20);
        taMotivo.setLineWrap(true);
        JScrollPane scrollMotivo = new JScrollPane(taMotivo);
        formPanel.add(scrollMotivo, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEmitir = new JButton("Emitir Laudo");
        btnEmitir.addActionListener(this::emitirLaudo);
        buttonPanel.add(btnEmitir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void emitirLaudo(ActionEvent e) {
        if (!rbAprovado.isSelected() && !rbNegado.isSelected()) {
            JOptionPane.showMessageDialog(this,"Selecione um status para o laudo!","Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String status = rbAprovado.isSelected() ? "Aprovado" : "Negado";
        String motivo = taMotivo.getText();

        if (status.equals("Negado") && motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Para laudos negados, é obrigatório informar o motivo!","Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Associa o laudo ao agendamento
        Vistoriador vistoriador;
        Laudo laudo = new Laudo(status, motivo, new Date(), vistoriador = new Vistoriador());
        agendamento.setLaudo(laudo);
        agendamento.setStatus("Concluído");

        JOptionPane.showMessageDialog(this,"Laudo emitido com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}