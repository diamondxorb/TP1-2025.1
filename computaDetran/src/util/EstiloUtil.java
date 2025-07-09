package util;

import javax.swing.*;
import java.awt.*;

//Classe com as cores e configurações do estilo visual do programa
public class EstiloUtil {
    public static final Color AMARELO = new Color(255, 204, 0);
    public static final Color PRETO = Color.BLACK;
    public static final Color BRANCO = Color.WHITE;
    public static final Color CINZA_CLARO = new Color(240, 240, 240);

    public static void aplicarEstilo(JFrame frame) {
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setBackground(BRANCO);

        //Configuração global para todos os botões
        UIManager.put("Button.background", AMARELO);
        UIManager.put("Button.foreground", PRETO);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 12));
        UIManager.put("Button.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(PRETO, 1), BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        //Configuração para campos de texto
        UIManager.put("TextField.background", BRANCO);
        UIManager.put("TextField.foreground", PRETO);
        UIManager.put("TextField.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(PRETO, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        //Configuração para labels
        UIManager.put("Label.foreground", PRETO);
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 12));

        //Configuração para títulos
        UIManager.put("TitledBorder.titleColor", PRETO);
        UIManager.put("TitledBorder.border", BorderFactory.createLineBorder(AMARELO, 2));
    }
}