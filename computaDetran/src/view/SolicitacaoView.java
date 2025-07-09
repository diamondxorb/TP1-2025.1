package view;

import model.Proprietario;
import model.Veiculo;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import util.EstiloUtil;
import util.FundoGradienteUtil;

public class SolicitacaoView extends JFrame {
    private JTextField txtData = new JTextField();
    private JTextField txtHorario = new JTextField();
    private JTextArea txtMotivo = new JTextArea(3, 20);
    private JCheckBox chkDocumento = new JCheckBox("Documentação em dia");
    private Proprietario proprietario;

    public SolicitacaoView(Proprietario proprietario) {
        this.proprietario = proprietario;

        setTitle("Nova Solicitação de Vistoria");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Configuração de estilo
        setContentPane(new FundoGradienteUtil());
        setLayout(new BorderLayout());
        EstiloUtil.aplicarEstilo(this);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField txtPlaca = new JTextField();
        JTextField txtModelo = new JTextField();

        JComboBox<String> cmbMotivo = new JComboBox<>();
        cmbMotivo.addItem("Transferência de propriedade");
        cmbMotivo.addItem("Mudança de município");
        cmbMotivo.addItem("Alteração de características");
        cmbMotivo.addItem("Segunda via do CRV");
        cmbMotivo.addItem("Baixa de veículo");
        cmbMotivo.addItem("Outros");

        JCheckBox chkDocumento = new JCheckBox(" Documentação em dia");
        JTextField txtLocal = new JTextField();
        JTextArea txtObservacoes = new JTextArea(3, 20);
        JScrollPane scrollObservacoes = new JScrollPane(txtObservacoes);

        panel.add(new JLabel("Placa do Veículo:"));
        panel.add(txtPlaca);
        panel.add(new JLabel("Modelo do Veículo:"));
        panel.add(txtModelo);
        panel.add(new JLabel("Motivo da Vistoria:"));
        panel.add(cmbMotivo);
        panel.add(new JLabel("Documentação:"));
        panel.add(chkDocumento);
        panel.add(new JLabel("Local Preferencial:"));
        panel.add(txtLocal);
        panel.add(new JLabel("Observações:"));
        panel.add(scrollObservacoes);

        JButton btnEnviar = new JButton("Solicitar Vistoria");
        btnEnviar.addActionListener(e -> {
            Veiculo veiculo = new Veiculo(txtPlaca.getText(), txtModelo.getText(), proprietario);
            veiculo.setDocumentoPago(chkDocumento.isSelected());

            String motivo = Objects.requireNonNull(cmbMotivo.getSelectedItem()).toString();
            String observacoes = txtObservacoes.getText().trim();

            if (!observacoes.isEmpty()) {
                motivo += " - " + observacoes;
            }

            String data = txtData.getText();
            String horario = txtHorario.getText();
            String placa = txtPlaca.getText();
            String modelo = txtModelo.getText();
            String motivoAgendamento = txtMotivo.getText();
            String local = txtLocal.getText();
            boolean documento = chkDocumento.isSelected();

            if(!documento) {
                JOptionPane.showMessageDialog(this, "Sua documentação precisa estar paga!", null, JOptionPane.WARNING_MESSAGE);
            } else if(!placa.isEmpty() && !modelo.isEmpty() && !local.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Solicitação enviada com sucesso!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

            /*
            if (AgendamentoController.salvarAgendamento(Agendamento ag = new Agendamento(data, horario, motivoAgendamento, placa, modelo, proprietario))) {
                JOptionPane.showMessageDialog(this, "Solicitação enviada com sucesso!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao enviar solicitação!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
             */
        });

        panel.add(new JLabel(""));
        panel.add(btnEnviar);

        add(panel);
    }
}