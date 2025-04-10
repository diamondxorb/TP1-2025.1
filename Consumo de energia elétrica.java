import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        double consumo, custoConsumo, dinheiro;

        consumo = Double.parseDouble(scanner.nextLine());

        if(consumo <= 500) {
            dinheiro = (consumo*0.02)+5;
        } else if(consumo <= 1000) {
            dinheiro = 55+((consumo-500)*0.05);
        } else {
            dinheiro = 355+((consumo-1000)*0.1);
        }

        custoConsumo = dinheiro-5;

        //O Locale.US antes das aspas no printf corrige a vírgula automática em números decimais//
        System.out.printf(Locale.US,"%.2f 5.00 %.2f", custoConsumo, dinheiro);
    }
}
