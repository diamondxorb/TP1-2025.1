import java.util.Scanner;

class Retangulo {
    private int comprimento = 1;
    private int largura = 1;

    public int perimetro() {
        return 2*(comprimento+largura);
    }
    public int area() {
        return comprimento*largura;
    }
    public int getComprimento() {
        return comprimento;
    }
    public int getLargura() {
        return largura;
    }
    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }
    public void setLargura(int largura) {
        this.largura = largura;
    }
}



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        String[] words;
        int i, tamComprimento, tamLargura;

        for(i=0; i<2; i++) {
            Retangulo retangulo = new Retangulo();

            input = scanner.nextLine();
            words = input.split(" ");
            tamComprimento = Integer.parseInt(words[0]);
            tamLargura = Integer.parseInt(words[1]);

            if(tamComprimento>0 && tamComprimento<20) {
                retangulo.setComprimento(tamComprimento);
            }
            if(tamLargura>0 && tamLargura<20) {
                retangulo.setLargura(tamLargura);
            }

            System.out.printf("%d %d %d %d\n", retangulo.getComprimento(), retangulo.getLargura(), retangulo.perimetro(), retangulo.area());
        }
    }
}
