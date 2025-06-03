import java.util.Scanner;

//Classe Veiculo
class Veiculo {
    protected String placa;
    protected int numeroPortas;
    protected float preco;

    public Veiculo() {}
    public Veiculo(String placa, int numeroPortas, float preco) {
        this.placa = placa;
        this.numeroPortas = numeroPortas;
        this.preco = preco;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public int getNumeroPortas() {
        return numeroPortas;
    }
    public void setNumeroPortas(int numeroPortas) {
        this.numeroPortas = numeroPortas;
    }
    public float getPreco() {
        return preco;
    }
    public void setPreco(float preco) {
        this.preco = preco;
    }
    public String toString() {
        return "Placa: " + getPlaca() + " Número de portas: " + getNumeroPortas() + " Preço: R$" + getPreco();
    }
}

//Classe Carro
class Carro extends Veiculo {
    private String cor;
    public Carro() {}
    public Carro(String placa, int numeroPortas, float preco, String cor) {
        super(placa, numeroPortas, preco);
        this.cor = cor;
    }
    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return super.toString() + " Cor: " + getCor();
    }
}

//Classe Moto
class Moto extends Veiculo {
    private int qtdeCilindradas;
    public Moto() {}
    public Moto(String placa, int numeroPortas, float preco, int qtdeCilindradas) {
        super(placa, numeroPortas, preco);
        this.qtdeCilindradas = qtdeCilindradas;
    }
    public int getQtdeCilindradas() {
        return qtdeCilindradas;
    }
    public void setQtdeCilindradas(int qtdeCilindradas) {
        this.qtdeCilindradas = qtdeCilindradas;
    }

    @Override
    public String toString() {
        return super.toString() + " Quantidade de cilindradas: " + getQtdeCilindradas();
    }
}

//Classe Principal
public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(entrada.next());
        veiculo.setNumeroPortas(entrada.nextInt());
        veiculo.setPreco(entrada.nextFloat());
        Carro carro = new Carro(entrada.next(), entrada.nextInt(), entrada.nextFloat(), entrada.next());
        Moto moto = new Moto(entrada.next(), entrada.nextInt(), entrada.nextFloat(), entrada.nextInt());

        System.out.println(veiculo);
        System.out.println(carro);
        System.out.println(moto);

    }

}
