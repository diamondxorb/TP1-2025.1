import java.util.Scanner;

//Informe o código aqui
interface Classificavel {
    boolean menorElemento(Classificavel obj);
}

class Classificador {
    public void ordena(Classificavel[] a) {
        Classificavel elem, menor;
        int pos;

        for(int i=0; i<a.length-1; i++) {
            elem = a[i];
            menor = a[i+1];
            pos = i+1;

            for(int j=i+2; j<a.length; j++) {
                if(a[j].menorElemento(menor)) {
                    menor = a[j];
                    pos = j;
                }
            }
            if(menor.menorElemento(elem)) {
                a[i] = a[pos];
                a[pos] = elem;
            }
        }
    }
}

class Produto implements Classificavel {
    private int codigo;
    private String nome;
    private double preco;

    public Produto(int codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    public int getCodigo() {return codigo;}
    public String getNome() {return nome;}
    public double getPreco() {return preco;}

    public void setCodigo(int codigo) {this.codigo = codigo;}
    public void setNome(String nome) {this.nome = nome;}
    public void setPreco(double preco) {this.preco = preco;}

    public String toString() {return getCodigo() + " " + getNome() + " " + getPreco();}

    @Override
    public boolean menorElemento(Classificavel obj) {
        Produto compara = (Produto) obj;

        return this.getCodigo() < compara.getCodigo();
    }
}

class Cliente implements Classificavel {
    private String cpf;
    private String nome;
    private String endereco;

    public Cliente(String cpf, String nome, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
    }

    public String getCpf() {return cpf;}
    public String getNome() {return nome;}
    public String getEndereco() {return endereco;}

    public void setCpf(String cpf) {this.cpf = cpf;}
    public void setNome(String nome) {this.nome = nome;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    public String toString() {return getCpf() + " " + getNome() + " " + getEndereco();}

    @Override
    public boolean menorElemento(Classificavel obj) {
        Cliente compara = (Cliente) obj;

        return getNome().compareTo(compara.getNome()) < 0;
    }
}

class Servico implements Classificavel {
    private String tipoServico;
    private double preco;
    private String data;

    public Servico(String tipoServico, double preco, String data) {
        this.tipoServico = tipoServico;
        this.preco = preco;
        this.data = data;
    }

    public String getTipoServico() {return tipoServico;}
    public double getPreco() {return preco;}
    public String getData() {return data;}

    public void setTipoServico(String tipoServico) {this.tipoServico = tipoServico;}
    public void setPreco(double preco) {this.preco = preco;}
    public void setData(String data) {this.data = data;}

    public String toString() {return getTipoServico() + " " + getPreco() + getData();}

    @Override
    public boolean menorElemento(Classificavel obj) {
        Servico compara = (Servico) obj;

        return getPreco() < compara.getPreco();
    }
}

//Classe principal
public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Classificador classifica = new Classificador();

        int numProduto = entrada.nextInt();
        int numCliente = entrada.nextInt();
        int numServico = entrada.nextInt();

        entrada.nextLine();

        Produto[] produtos = new Produto[numProduto];

        for(int i = 0; i < numProduto; i++){
            int codigo = entrada.nextInt();
            String nome = entrada.next();
            double preco = entrada.nextDouble();
            entrada.nextLine();

            produtos[i] = new Produto(codigo, nome, preco);
            //produtos[i] = new Produto(entrada.nextInt(), entrada.next(), entrada.nextDouble());
        }

        Cliente[] clientes = new Cliente[numCliente];

        for(int i = 0; i < numCliente; i++){
            clientes[i] = new Cliente(entrada.next(), entrada.next(), entrada.next());
        }

        Servico[] servicos = new Servico[numServico];

        for(int i = 0; i < numServico; i++){
            String tipoServico = entrada.next();
            double preco = entrada.nextDouble();
            String data = entrada.nextLine();

            servicos[i] = new Servico(tipoServico, preco, data);
            //servicos[i] = new Servico(entrada.next(), entrada.nextDouble(), entrada.next());
        }

        classifica.ordena(produtos);
        classifica.ordena(clientes);
        classifica.ordena(servicos);

        for(int i = 0; i < numProduto; i++) {
            System.out.println(produtos[i].toString());
        }

        for(int i = 0; i < numCliente; i++) {
            System.out.println(clientes[i].toString());
        }

        for(int i = 0; i < numServico; i++) {
            System.out.println(servicos[i].toString());
        }

    }

}
