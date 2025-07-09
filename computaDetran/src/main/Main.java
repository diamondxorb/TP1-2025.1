package main;

import controller.Conexao;
import view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e){
            e.printStackTrace();
        }

        //Inicializa o banco de dados
        Conexao.inicializarBanco();

        //Roda a tela inicial de Login
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });

    }
}
