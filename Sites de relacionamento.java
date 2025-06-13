import java.util.Scanner;
import java.util.ArrayList;

abstract class Contato {
    protected String nome;
    protected String apelido;
    protected String email;
    protected String aniversario;

    public Contato(String nome, String apelido, String email, String aniversario) {
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.aniversario = aniversario;
    }

    public String getNome() { return nome; }
    public String getApelido() { return apelido; }
    public String getEmail() { return email; }
    public String getAniversario() { return aniversario; }

    public void setNome(String nome) { this.nome = nome; }
    public void setApelido(String apelido) { this.apelido = apelido; }
    public void setEmail(String email) { this.email = email; }
    public void setAniversario(String aniversario) { this.aniversario = aniversario; }

    public String imprimirBasico() {
        return "Nome: " + nome + "\n" + "Apelido: " + apelido + "\n" + "Email: " + email + "\n" + "Aniversário: " + aniversario + "\n";
    }

    abstract String imprimirContato();
}

class Amigo extends Contato {
    protected int grau;
    public Amigo(String nome, String apelido, String email, String aniversario, int grau) {
        super(nome, apelido, email, aniversario);
        this.grau = grau;
    }

    public int getGrau() { return grau; }
    public void setGrau(int grau) { this.grau = grau; }

    @Override
    String imprimirContato() {
        String aux = "";
        if(grau == 1) {
            aux = "Melhor Amigo";
        } else if(grau == 2) {
            aux = "Amigo";
        } else if(grau == 3) {
            aux = "Conhecido";
        }
        return imprimirBasico() + "Grau: " + aux;
    }
}

class Familia extends Contato {
    protected String parentesco;

    public Familia(String nome, String apelido, String email, String aniversario, String parentesco) {
        super(nome, apelido, email, aniversario);
        this.parentesco = parentesco;
    }

    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }

    @Override
    String imprimirContato() {
        return imprimirBasico() + "Parentesco:" + parentesco;
    }
}

class ColegasDeTrabalho extends Contato {
    protected String tipo;

    public ColegasDeTrabalho(String nome, String apelido, String email, String aniversario, String tipo) {
        super(nome, apelido, email, aniversario);
        this.tipo = tipo;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    String imprimirContato() {
        return imprimirBasico() + "Relacionamento de trabalho:" + tipo;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Contato> contatos = new ArrayList<>();

        int option;
        do {
            option = scanner.nextInt();
            // Para limpar o buffer do teclado
            if(option != 1) {
                scanner.nextLine();
            }

            switch(option) {
                case 1:
                    int type = scanner.nextInt();
                    scanner.nextLine();

                    String nome = scanner.next();
                    String apelido = scanner.next();
                    String email = scanner.next();
                    String aniversario = scanner.next();

                    if(type == 1) {
                        int grau = scanner.nextInt();
                        scanner.nextLine();
                        Amigo amigo = new Amigo(nome, apelido, email, aniversario, grau);
                        contatos.add(amigo);
                    } else if(type == 2) {
                        String parentesco = scanner.nextLine();
                        Familia familia = new Familia(nome, apelido, email, aniversario, parentesco);
                        contatos.add(familia);
                    } else if(type == 3) {
                        String tipo = scanner.nextLine();
                        ColegasDeTrabalho colegaDeTrabalho = new ColegasDeTrabalho(nome, apelido, email, aniversario, tipo);
                        contatos.add(colegaDeTrabalho);
                    }
                    break;
                case 2:
                    for (Contato contato : contatos) {
                        System.out.println(contato.imprimirContato());
                    }
                    break;
                case 3:
                    for(Contato contato : contatos) {
                        if(contato instanceof Familia) {
                            System.out.println(contato.imprimirContato());
                        }
                    }
                    break;
                case 4:
                    for(Contato contato : contatos) {
                        if(contato instanceof Amigo) {
                            System.out.println(contato.imprimirContato());
                        }
                    }
                    break;
                case 5:
                    for(Contato contato : contatos) {
                        if(contato instanceof ColegasDeTrabalho) {
                            System.out.println(contato.imprimirContato());
                        }
                    }
                    break;
                case 6:
                    for(Contato contato : contatos) {
                        if(contato instanceof Amigo) {
                            Amigo amigo = (Amigo) contato;
                            if(amigo.getGrau() == 1) {
                                System.out.println(contato.imprimirContato());
                            }
                        } else if(contato instanceof Familia) {
                            Familia familia = (Familia) contato;
                            // O uso do trim() é necessário pois na leitura do dado é utilizado o nextLine() que incluí um espaço na frente da entrada
                            if(familia.getParentesco().trim().equals("irmão")) {
                                System.out.println(contato.imprimirContato());
                            }
                        } else if(contato instanceof ColegasDeTrabalho) {
                            ColegasDeTrabalho colegaDeTrabalho = (ColegasDeTrabalho) contato;
                            // O mesmo uso do trim() é utilizado
                            if(colegaDeTrabalho.getTipo().trim().equals("colega")) {
                                System.out.println(contato.imprimirContato());
                            }
                        }
                    }
                    break;
                case 7:
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(contatos.get(index-1).imprimirContato());
                    break;
                case 8:
                        break;
            }
        } while (option != 8);
    }
}
