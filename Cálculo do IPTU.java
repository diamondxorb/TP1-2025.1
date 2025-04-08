import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int tipoLote, areaLote;
        double iptu;
        
        tipoLote = scanner.nextInt();
        areaLote = scanner.nextInt();
        
        if((tipoLote!=1 && tipoLote!=2) || areaLote<=0) {
            System.out.print("Entrada inválida!\n");
        } else {
            if(tipoLote==1) {
                if (areaLote<200) {
                    iptu = areaLote;
                } else {
                    iptu = areaLote*1.2;
                }
            } else {
                if(areaLote<200) {
                    iptu = areaLote*1.1;
                } else {
                    iptu = areaLote*1.3;
                }
            }
            
            System.out.printf("%.2f\n", iptu);
        }
    }
}
