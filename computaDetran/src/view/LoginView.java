package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import model.Proprietario;
import util.EstiloUtil;
import util.FundoGradienteUtil;

import controller.UsuarioController;

//Classe inicial do programa, contém os campos para fazer o login ou caso não tenha cadastro, criar sua conta
public class LoginView extends JFrame {
    private JTextField txtCpf;
    private JPasswordField txtSenha;
    private JButton btnLogin, btnCadastro;

    public LoginView() {
        setTitle("Login - computaDetran");
        setSize(450, 500);
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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("computaDetran", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitulo, gbc);

        //Imagem
        gbc.gridy = 1;
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/detran.png")));
            Image image = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);

            JLabel lblIcon = new JLabel(scaledIcon);
            lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
            lblIcon.setVerticalAlignment(SwingConstants.CENTER);
            panel.add(lblIcon, gbc);
        } catch (Exception e) {
            System.err.println("Imagem não encontrada: " + e.getMessage());
        }

        //Campo do CPF
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(15);
        panel.add(txtCpf, gbc);

        //Campo da Senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        txtSenha = new JPasswordField(15);
        panel.add(txtSenha, gbc);

        //Botão Login
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(100, 30));
        panel.add(btnLogin, gbc);

        //Botão Cadastro
        gbc.gridy = 5;
        btnCadastro = new JButton("Criar nova conta");
        btnCadastro.setPreferredSize(new Dimension(150, 25));
        btnCadastro.setContentAreaFilled(false);
        btnCadastro.setBorderPainted(false);
        btnCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnCadastro, gbc);

        //Configurações estilo
        txtCpf.setBackground(Color.WHITE);
        txtSenha.setBackground(Color.WHITE);

        btnLogin.setBackground(EstiloUtil.AMARELO);
        btnLogin.setForeground(EstiloUtil.PRETO);

        btnCadastro.setBackground(EstiloUtil.BRANCO);
        btnCadastro.setForeground(EstiloUtil.PRETO);


        //Adiciona ações
        btnLogin.addActionListener(this::realizarLogin);
        btnCadastro.addActionListener(e -> abrirTelaCadastro());

        add(panel);
    }

    //Função para realizar o login, checa se todos os dados foram preenchidos, se existe uma conta e se a senha está correta
    private void realizarLogin(ActionEvent e) {
        String cpf = txtCpf.getText();
        String senha = new String(txtSenha.getPassword());

        if (cpf.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Preencha todos os campos!","Erro de Login", JOptionPane.ERROR_MESSAGE);
            return;
        }


        UsuarioController usuarioController = new UsuarioController();
        boolean cadastroSucesso = false;

        if(!usuarioController.usuarioExiste(cpf)) {
            JOptionPane.showMessageDialog(this, "Sua conta não existe!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        } else if(!usuarioController.senhaCorreta(cpf, senha)) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        } else {
            cadastroSucesso = true;
        }

        String perfil = usuarioController.retornaPerfil(cpf);
        if(cadastroSucesso) {
            if (perfil != null) {
                abrirTelaAposLogin(cpf, perfil);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao identificar perfil do usuário!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Função para fechar a tela de login e abrir a de cadastro
    private void abrirTelaCadastro() {
        this.dispose();
        new CadastroView().setVisible(true);
    }

    //Função para fechar a tela de login e abrir a tela respectiva ao perfil, utiliza manipulação de string
    private void abrirTelaAposLogin(String cpf, String perfil) {
        this.dispose();

        switch (perfil.toUpperCase()) {
            case "PROPRIETÁRIO":
                UsuarioController usuarioController = new UsuarioController();
                String nome = usuarioController.retornaNome(cpf);
                Proprietario proprietario = new Proprietario();
                proprietario.setNome(nome);
                proprietario.setCpf(cpf);
                new ProprietarioView(proprietario).setVisible(true);
                break;
            case "VISTORIADOR":
                new VistoriadorView(cpf).setVisible(true);
                break;
            case "ATENDENTE":
                new AtendenteView().setVisible(true);
                break;
        }
    }
}