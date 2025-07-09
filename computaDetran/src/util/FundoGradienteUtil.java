package util;

import javax.swing.*;
import java.awt.*;

//Classe que cria o fundo padrão amarelo do programa
public class FundoGradienteUtil extends JPanel {
    private final Color corInicial;
    private final Color corFinal;

    public FundoGradienteUtil() {
        this.corInicial = new Color(255, 204, 0);
        this.corFinal = Color.WHITE;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        GradientPaint gp = new GradientPaint(0, 0, corInicial,0, getHeight(), corFinal);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}