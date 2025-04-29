import java.util.Scanner;

class Emergencia {
    public String nomeChamador;
    public String telefoneChamador;
    public String localEmergencia;
    public String dataHoraRelato;
    public String naturezaEmergencia;
    public String tipoReposta;
    public String statusReposta;
    public boolean chamadaMovel;
    public String coordenadasGPS;

    public void mostrarEmergencias() {
        System.out.print("--- Informações da Emergência ---\n\n");
        System.out.println("Nome do Chamador: " + nomeChamador);
        System.out.println("Telefone: " + telefoneChamador);
        System.out.println("Local da Emergência: " + localEmergencia);
        System.out.println("Data/Hora do Relato: " + dataHoraRelato);
        System.out.println("Natureza da Emergência: " + naturezaEmergencia);
        System.out.println("Tipo de Resposta: " + tipoReposta);
        System.out.println("Status da Resposta: " + statusReposta);
        if(chamadaMovel) {
            System.out.println("Chamada via celular: Sim");
        } else {
            System.out.println("Chamada via celular: Não");
        }
        if(chamadaMovel) {
            System.out.println("Coordenadas GPS: " + coordenadasGPS);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Emergencia emergencia = new Emergencia();

        int i, num;

        num = scanner.nextInt();
        scanner.nextLine();

        for(i=1; i<=num; i++) {
            if(i>1) {
                System.out.print("\n");
            }
            emergencia.nomeChamador = scanner.nextLine();
            emergencia.telefoneChamador = scanner.nextLine();
            emergencia.localEmergencia = scanner.nextLine();
            emergencia.dataHoraRelato = scanner.nextLine();
            emergencia.naturezaEmergencia = scanner.nextLine();
            emergencia.tipoReposta = scanner.nextLine();
            emergencia.statusReposta = scanner.nextLine();
            emergencia.chamadaMovel = scanner.nextLine().toLowerCase().equals("sim");
            if(emergencia.chamadaMovel) {
                emergencia.coordenadasGPS = scanner.nextLine();
            }

            System.out.printf("Emergência #%d\n\n", i);

            emergencia.mostrarEmergencias();
        }
    }
}
