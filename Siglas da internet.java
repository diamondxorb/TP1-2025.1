import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Usando ArrayList para criar uma lista e poder usar métodos como append, etc.//
        ArrayList<String> listaSigla = new ArrayList<>();
        ArrayList<String> listaTraducao = new ArrayList<>();
        String input, palavra;
        //StringBuilder para poder ser alterada durante o programa//
        StringBuilder output, fraseFinal;
        //String só para poder separar substrings de uma string com espaços//
        String[] words;

        int i, j, num, quantidade = 0;

        num = scanner.nextInt();
        scanner.nextLine();
        output = new StringBuilder();

        for(i=0; i<num; i++) {
            //Pega um input do teclado e faz um split entre " " retirando eles//
            input = scanner.nextLine();
            words = input.split(" ");
            listaSigla.add(words[0]);
            listaTraducao.add(words[1]);
        }
        fraseFinal = new StringBuilder(scanner.nextLine());

        //Uso do Matcher e o Pattern para contar quantas vezes aparece uma substring numa string maior//
        Matcher espaco = Pattern.compile(" ").matcher(fraseFinal);
        while(espaco.find()) {
            quantidade++;
        }
        quantidade++;

        //Por algum motivo... Esse exercício é muito chato de se lidar com letras maiúsculas, a forma que fiz para contornar foi utilizar (if i==0)//
        for(i=0; i<quantidade; i++) {
            words = fraseFinal.toString().split(" ");
            palavra = words[i];
            if(i==0) {
                palavra = palavra.toLowerCase();
            }
            if(listaSigla.contains(palavra)) {
                j = listaSigla.indexOf(palavra);
                output.append(listaTraducao.get(j));
            } else {
                if(i==0) {
                    //Separa uma substring de tamanho um, no caso a primeira letra, muda para maiúsculo e concatena com a substring restante//
                    palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1));
                }
                output.append(palavra);
            }
            output.append(" ");
        }
        //Só para tirar o último espaço que fica no output//
        output.deleteCharAt(output.length()-1);

        System.out.println(output);
    }
}
