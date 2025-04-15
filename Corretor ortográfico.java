import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int i, j, k, num, tam;
        String sugestao = null;
        String palavraDigitada;
        ArrayList<String> listaPalavras = new ArrayList<>();

        num = scanner.nextInt();
        scanner.nextLine();

        for(i=0; i<num; i++) {
            listaPalavras.add(scanner.nextLine());
        }

        palavraDigitada = scanner.nextLine();
        tam = palavraDigitada.length()-1;

        if(listaPalavras.contains(palavraDigitada)) {
            System.out.print("palavra correta\n");
        } else {
            System.out.print("palavra incorreta\n");

            for(i=0; i<tam; i++) {
                //toCharArray() serve para transformar uma String em um array de char, tal qual em C//
                char[] chars = palavraDigitada.toCharArray();
                //char temporário só para ficar trocando um char pelo próximo//
                char temp = chars[i];
                chars[i] = chars[i+1];
                chars[i+1] = temp;
                //Cria a String, anagrama utilizando o array chars, o contrário do toCharArray()//
                String anagrama = new String(chars);

                for(j=0; j<num; j++) {
                    if(listaPalavras.contains(anagrama)) {
                        k = listaPalavras.indexOf(anagrama);
                        sugestao = listaPalavras.get(k);
                        break;
                    }
                }
                if(sugestao != null) {
                    break;
                }
            }
            if(sugestao != null) {
                System.out.printf("%s?\n", sugestao);
            } else {
                System.out.print("nenhuma sugestão\n");
            }
        }
    }
}
