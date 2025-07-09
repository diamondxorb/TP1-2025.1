package view;

import javax.swing.*;
import java.awt.*;
import model.Agendamento;
import util.EstiloUtil;
import util.FundoGradienteUtil;

public class DetalhesAgendamentoView extends JFrame {
    public DetalhesAgendamentoView(Agendamento agendamento) {
        setTitle("Detalhes do Agendamento n° " + agendamento.getId());
        setSize(600, 400);
        setLocationRelativeTo(null);

        //Configuração das cores e estilo
        setContentPane(new FundoGradienteUtil());
        setLayout(new BorderLayout());
        EstiloUtil.aplicarEstilo(this);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID:"));
        panel.add(new JLabel(String.valueOf(agendamento.getId())));

        panel.add(new JLabel("Data:"));
        panel.add(new JLabel(agendamento.getData()));

        panel.add(new JLabel("Horário:"));
        panel.add(new JLabel(agendamento.getHorario()));

        panel.add(new JLabel("Motivo:"));
        panel.add(new JLabel(agendamento.getMotivoAgendamento()));

        panel.add(new JLabel("Atendente:"));
        panel.add(new JLabel(agendamento.getAtendente().getNome()));

        panel.add(new JLabel("Vistoriador:"));
        panel.add(new JLabel(agendamento.getVistoriador().getNome()));

        panel.add(new JLabel("Status:"));
        panel.add(new JLabel(agendamento.getStatus()));

        if (agendamento.getLaudo() != null) {
            panel.add(new JLabel("Laudo:"));
            panel.add(new JLabel(agendamento.getLaudo().getStatus()));

            panel.add(new JLabel("Motivo:"));
            panel.add(new JLabel(agendamento.getLaudo().getMotivo()));

            panel.add(new JLabel("Emissão do Laudo:"));
            panel.add(new JLabel(agendamento.getLaudo().getDataEmissaoFormatada()));
        }

        add(panel);
    }

}