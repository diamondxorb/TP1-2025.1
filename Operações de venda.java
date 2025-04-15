import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input, pesquisa;
        String[] words;
        ArrayList<String> listaProdutos = new ArrayList<>();
        ArrayList<Integer> listaQuantidades = new ArrayList<>();

        int i, j, num, venda;

        num = scanner.nextInt();
        scanner.nextLine();

        for(i=0; i<num; i++) {
            input = scanner.nextLine();
            words = input.split(" ");
            listaProdutos.add(words[0]);
            listaQuantidades.add(Integer.parseInt(words[1]));
        }

        while(true) {
            //Uso do next() e não o nextLine() para pegar apenas o que é escrito até algum espaço e não até a quebra de linha//
            pesquisa = scanner.next();

            if(pesquisa.equals("fim")) {
                break;
            } else {
                if(listaProdutos.contains(pesquisa)) {
                    j = listaProdutos.indexOf(pesquisa);

                    venda = scanner.nextInt();
                    scanner.nextLine();

                    System.out.printf("%s %d\n", pesquisa, venda);

                    listaQuantidades.set(j, listaQuantidades.get(j) - venda);
                } else {
                    System.out.printf("%s produto não encontrado\n", pesquisa);
                }
            }
        }
        for(i=0; i<num; i++) {
            System.out.printf("%s %d\n", listaProdutos.get(i), listaQuantidades.get(i));
        }
    }
}
