package view;

import controller.AgendamentoController;
import controller.UsuarioController;
import model.Agendamento;
import util.EstiloUtil;
import util.FundoGradienteUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

//Classe com a tela principal do Vistoriador, onde é apresentado os agendamentos relacionados a ele
//Opções de emitir laudo, visualizar detalhes e atualizar o banco de dados são mostradas
public class VistoriadorAgendamentosView extends JFrame {
    private JTable tabelaAgendamentos;
    private final String nome;
    private List<Agendamento> agendamentos;

    public VistoriadorAgendamentosView(String cpf) throws SQLException {
        UsuarioController uc = new UsuarioController();
        nome = uc.retornaNome(cpf);
        setTitle("Agendamentos do Dia - " + nome);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //Configuração das cores e estilo
        setContentPane(new FundoGradienteUtil());
        setLayout(new BorderLayout());
        EstiloUtil.aplicarEstilo(this);

        initComponents();
        setVisible(true);
    }


    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setOpaque(false);

        //Painel de botões para visualizar os detalhes e emitir laudos
        tabelaAgendamentos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaAgendamentos);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVisualizar = new JButton("Visualizar Detalhes");
        JButton btnLaudo = new JButton("Emitir Laudo");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnSair = new JButton("Sair");

        btnVisualizar.addActionListener(this::visualizarDetalhes);
        btnLaudo.addActionListener(this::emitirLaudo);
        btnAtualizar.addActionListener(e-> {
            try {
                carregarDados();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,"Erro ao carregar agendamentos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnSair.addActionListener(e -> {sair();});

        buttonPanel.add(btnVisualizar);
        buttonPanel.add(btnLaudo);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnSair);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        try {
            carregarDados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Erro ao carregar agendamentos: " + e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
        }

        add(mainPanel);
    }

    //Função que carrega o banco de dados e a lista de agendamentos
    private void carregarDados() throws SQLException {
        try {
            //Atualiza a lista de agendamentos
            UsuarioController uc = new UsuarioController();
            int id = uc.retornaId(nome);

            agendamentos = AgendamentoController.listarAgendamentosPorVistoriador(id);
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID", "Data", "Horário", "Motivo", "Atendente"});
            for (Agendamento ag : agendamentos) {
                model.addRow(new Object[]{
                        ag.getId(),
                        ag.getData(),
                        ag.getHorario(),
                        ag.getMotivoAgendamento(),
                        ag.getAtendente().getNome() != null ? ag.getAtendente().getNome() : "N/A"
                });
            }
            tabelaAgendamentos.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Função para visualizar os detalhes de algum agendamento escolhido
    private void visualizarDetalhes(ActionEvent e) {
        int selectedRow = tabelaAgendamentos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um agendamento primeiro!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int agendamentoId = (int) tabelaAgendamentos.getValueAt(selectedRow, 0);
        Agendamento agendamento = buscarAgendamentoPorId(agendamentoId);

        if (agendamento != null) {
            new DetalhesAgendamentoView(agendamento).setVisible(true);
        }
    }

    //Função para emitir o laudo de algum agendamento escolhido
    private void emitirLaudo(ActionEvent e) {
        int selectedRow = tabelaAgendamentos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um agendamento primeiro!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int agendamentoId = (int) tabelaAgendamentos.getValueAt(selectedRow, 0);
        Agendamento agendamento = buscarAgendamentoPorId(agendamentoId);

        if(agendamento != null) {
            new EmitirLaudoView(agendamento, nome).setVisible(true);
        }
    }

    //Função auxiliar para buscar um agendamento por ID
    private Agendamento buscarAgendamentoPorId(int id) {
        return agendamentos.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    //Função que fecha a janela e volta para a tela de login
    private void sair() {
        this.dispose();

        EventQueue.invokeLater(()-> {
            try {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
