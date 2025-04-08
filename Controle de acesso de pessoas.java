import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Define o programa inteiro com a localização US, as saídas também saem com ponto//
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        
        char person;
        int mulheres, homens;
        double totalArrecadado, valorM, valorH;
        
        mulheres = 0;
        homens = 0;
        
        while (true) {
            person = scanner.next().charAt(0);
            
            if(person == 'h') {
                homens++;
            } else if(person == 'm') {
                mulheres++;
            } else if(person == 'q') {
                break;
            }
        }
        
        valorH = homens*12.5;
        valorM = mulheres*7.4;
        totalArrecadado = valorM + valorH;
        
        System.out.printf("%d %d\n", homens, mulheres);
        System.out.printf("%.2f %.2f %.2f", valorH, valorM, totalArrecadado);
    }
}
