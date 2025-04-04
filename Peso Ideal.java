import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Uso do .useLocale(Locale.US) para evitar que o scanner não entenda números decimais utilizando ponto//
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        double altura, peso_ideal;
        char sexo;
        boolean fag = false;

        altura = scanner.nextDouble();
        sexo = scanner.next().charAt(0);
        
        if((sexo == 'm' || sexo == 'f') && altura>0) {
            fag = true;
        }

        if(fag) {
            if(sexo == 'm') {
                peso_ideal = (altura*72.7)-58;
            } else {
                peso_ideal = (altura*62.1)-44.7;
            }
            System.out.printf("%.1f kg\n", peso_ideal);
        } else {
            System.out.print("Entrada inválida!\n");
        }
    }
}
