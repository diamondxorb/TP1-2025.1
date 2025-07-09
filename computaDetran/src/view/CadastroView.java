package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ItemEvent;
import java.util.Date;
import javax.swing.text.MaskFormatter;

import controller.UsuarioController;
import util.EstiloUtil;
import util.FundoGradienteUtil;

//Classe responsável por realizar o cadastro de um novo usuário do programa, seja Proprietário, Atendente ou Vistoriador
public class CadastroView extends JFrame {
    private JTextField txtNome, txtCpf, txtEndereco, txtEmail, txtCelular;
    private JFormattedTextField txtDataNascimento;
    private JPasswordField txtSenha;
    private JComboBox<String> cbPerfil;
    private JButton btnCadastrar, btnVoltar;

    private JPanel panelCamposEspecificos;
    private JTextField txtCadastroDetran, txtAssinatura;// Para vistoriador
    private JTextField txtNumIdentif; // Para atendente

    public CadastroView() {
        setTitle("Cadastro - computaDetran");
        setSize(600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Configuração das cores e estilo
        setContentPane(new FundoGradienteUtil());
        setLayout(new BorderLayout());
        EstiloUtil.aplicarEstilo(this);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        //Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Criar nova conta", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitulo, gbc);

        //Campos do formulário
        gbc.gridwidth = 1;

        //Nome completo
        gbc.gridy = 1;
        panel.add(new JLabel("Nome completo:"), gbc);
        gbc.gridy = 2;
        txtNome = new JTextField(20);
        panel.add(txtNome, gbc);

        //CPF
        gbc.gridy = 3;
        panel.add(new JLabel("CPF:"), gbc);
        gbc.gridy = 4;
        txtCpf = new JTextField(20);
        panel.add(txtCpf, gbc);

        //Data de Nascimento
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Data de Nascimento:"), gbc);

        gbc.gridy = 6;
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
            txtDataNascimento = new JFormattedTextField(mask);
            txtDataNascimento.setColumns(10);
            panel.add(txtDataNascimento, gbc);
        } catch (ParseException e) {
            txtDataNascimento = new JFormattedTextField();
            panel.add(txtDataNascimento, gbc);
        }

        //Endereço
        gbc.gridy = 7;
        panel.add(new JLabel("Endereço:"), gbc);
        gbc.gridy = 8;
        txtEndereco = new JTextField(20);
        panel.add(txtEndereco, gbc);

        //E-mail
        gbc.gridy = 9;
        panel.add(new JLabel("E-mail:"), gbc);
        gbc.gridy = 10;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        //Celular
        gbc.gridy = 11;
        panel.add(new JLabel("Celular:"), gbc);
        gbc.gridy = 12;
        txtCelular = new JTextField(20);
        panel.add(txtCelular, gbc);

        //Senha
        gbc.gridy = 13;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridy = 14;
        txtSenha = new JPasswordField(20);
        panel.add(txtSenha, gbc);

        //Tipo de conta: proprietário, atendente, vistoriador
        gbc.gridy = 15;
        panel.add(new JLabel("Tipo de conta:"), gbc);
        gbc.gridy = 16;
        String[] perfis = {"Proprietário", "Atendente", "Vistoriador"};
        cbPerfil = new JComboBox<>(perfis);
        panel.add(cbPerfil, gbc);

        //Painel para campos específicos do perfil (inicialmente vazio)
        gbc.gridy = 17;
        panelCamposEspecificos = new JPanel();
        panelCamposEspecificos.setLayout(new GridBagLayout());
        panel.add(panelCamposEspecificos, gbc);

        //Listener para mudança no tipo de perfil
        cbPerfil.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                atualizarCamposEspecificos();
            }
        });

        gbc.gridy = 18;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnVoltar = new JButton("Voltar");
        btnCadastrar = new JButton("Cadastrar");

        btnVoltar.addActionListener(e -> voltarParaLogin());
        btnCadastrar.addActionListener(this::realizarCadastro);

        botoesPanel.add(btnVoltar);
        botoesPanel.add(btnCadastrar);
        panel.add(botoesPanel, gbc);

        add(panel);
    }

    //Função para mudar a tela conforme a escolha do perfil do usuário
    private void atualizarCamposEspecificos() {
        panelCamposEspecificos.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        String perfil = (String) cbPerfil.getSelectedItem();

        switch (perfil) {
            case "Atendente":
                gbc.gridy = 0;
                panelCamposEspecificos.add(new JLabel("Número de Identificação:"), gbc);

                gbc.gridy = 1;
                txtNumIdentif = new JTextField(20);
                panelCamposEspecificos.add(txtNumIdentif, gbc);
                break;

            case "Vistoriador":
                JPanel panelVistoriador = new JPanel(new GridLayout(0, 2, 10, 5));

                JPanel panelDetran = new JPanel(new BorderLayout(5, 5));
                panelDetran.add(new JLabel("Cadastro Detran:"), BorderLayout.NORTH);
                txtCadastroDetran = new JTextField(15);
                panelDetran.add(txtCadastroDetran, BorderLayout.CENTER);

                JPanel panelAssinatura = new JPanel(new BorderLayout(5, 5));
                panelAssinatura.add(new JLabel("Assinatura:"), BorderLayout.NORTH);
                txtAssinatura = new JTextField(15);
                panelAssinatura.add(txtAssinatura, BorderLayout.CENTER);

                panelVistoriador.add(panelDetran);
                panelVistoriador.add(panelAssinatura);

                panelCamposEspecificos.add(panelVistoriador);
                break;

            //Proprietário não tem campos específicos adicionais
        }

        panelCamposEspecificos.revalidate();
        panelCamposEspecificos.repaint();

        //Ajusta o tamanho da janela conforme necessário
        pack();
    }

    //Função para efetivar o cadastro do usuário
    private void realizarCadastro(ActionEvent e) {
        if(!validarCampos()) {
            return;
        }

        //Validação da data de nascimento
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        Date dataNasc;

        try {
            dataNasc = sdf.parse(txtDataNascimento.getText());
            //Verifica se a data é no passado
            if (dataNasc.after(new Date())) {
                JOptionPane.showMessageDialog(this,"Data de nascimento deve ser no passado!","Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (ParseException ex) {
            //Verifica se a data não está no formato dd/MM/yyyy
            JOptionPane.showMessageDialog(this,"Data de nascimento inválida! Use o formato dd/MM/yyyy","Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String dataNascStr = txtDataNascimento.getText();
        String endereco = txtEndereco.getText();
        String email = txtEmail.getText();
        String celular = txtCelular.getText();
        String senha = txtSenha.getText();
        String perfil = (String) cbPerfil.getSelectedItem();

        int numIdentif = 0;
        int cadastroDetran = 0;
        String assinatura = null;

        if (perfil.equals("Vistoriador")) {
            cadastroDetran = Integer.parseInt(txtCadastroDetran.getText());
            assinatura = txtAssinatura.getText();
            if (cadastroDetran==0) {
                JOptionPane.showMessageDialog(this,"Cadastro no Detran é obrigatório!","Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (assinatura.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Assinatura é obrigatória!", "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (perfil.equals("Atendente")) {
            numIdentif = Integer.parseInt(txtNumIdentif.getText());
            if (numIdentif==0) {
                JOptionPane.showMessageDialog(this,"Número de Identificação é obrigatório!","Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        UsuarioController controller = new UsuarioController();
        boolean cadastroSucesso = false;

        try {
            switch (perfil) {
                case "Proprietário":
                    cadastroSucesso = controller.cadastrarPessoa(nome, cpf, dataNascStr, endereco, email, celular, senha);
                    break;
                case "Atendente":
                    cadastroSucesso = controller.cadastrarPessoa(nome, cpf, dataNascStr, endereco, email, celular, senha, numIdentif);
                    break;
                case "Vistoriador":
                    cadastroSucesso = controller.cadastrarPessoa(nome, cpf, dataNascStr, endereco, email, celular, senha, cadastroDetran, assinatura);
                    break;
            }

            if (cadastroSucesso) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                voltarParaLogin();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar. Verifique os dados e tente novamente!", "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro durante o cadastro: " + ex.getMessage(), "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Função que verifica se nenhum campo foi deixado em branco
    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty() ||
                txtDataNascimento.getText().isEmpty() || txtEndereco.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtCelular.getText().isEmpty() ||
                txtSenha.getPassword().length == 0) {

            JOptionPane.showMessageDialog(this,"Preencha todos os campos!","Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtSenha.getPassword().length < 4 || txtSenha.getPassword().length > 10) {
            JOptionPane.showMessageDialog(this,"A senha deve ter entre 4 a 10 caracteres!","Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    //Função que fecha a tela de cadastro e volta para o login
    private void voltarParaLogin() {
        this.dispose();
        new LoginView().setVisible(true);
    }
}