import java.util.Scanner;

//Classes
class Funcionario {
    private String nome;
    private String dataNascimento;
    private float salario;

    public Funcionario() {}
    public Funcionario(String nome, String dataNascimento, float salario) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public float getSalario() {
        return salario;
    }
    public void setSalario(float salario) {
        this.salario = salario;
    }
}

class Chefe extends Funcionario {
    private String departamento;

    public Chefe() {}
    public Chefe(String nome, String dataNascimento, float salario, String departamento) {
        super(nome, dataNascimento, salario);
        this.departamento = departamento;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}

class Vendedor extends Funcionario {
    private float comissaoVenda;
    private int quantidadeVendas;

    public Vendedor() {}
    public Vendedor(String nome, String dataNascimento, float salario, float comissaoVenda, int quantidadeVendas) {
        super(nome, dataNascimento, salario);
        salario += comissaoVenda*quantidadeVendas;
        super.setSalario(salario);
        this.comissaoVenda = comissaoVenda;
        this.quantidadeVendas = quantidadeVendas;
    }

    public float getComissaoVenda() {
        return comissaoVenda;
    }
    public void setComissaoVenda(float comissaoVenda) {
        this.comissaoVenda = comissaoVenda;
    }
    public int getQuantidadeVendas() {
        return quantidadeVendas;
    }
    public void setQuantidadeVendas(int quantidadeVendas) {
        this.quantidadeVendas = quantidadeVendas;
    }
}

class Operario extends Funcionario {
    private float valorProducao;
    private int quantidadeProduzida;

    public Operario() {}
    public Operario(String nome, String dataNascimento, float salario, float valorProducao, int quantidadeProduzida) {
        super(nome, dataNascimento, salario);
        salario += valorProducao*quantidadeProduzida;
        super.setSalario(salario);
        this.valorProducao = valorProducao;
        this.quantidadeProduzida = quantidadeProduzida;
    }
    public float getValorProducao() {
        return valorProducao;
    }
    public void setValorProducao(float valorProducao) {
        this.valorProducao = valorProducao;
    }
    public int getQuantidadeProduzida() {
        return quantidadeProduzida;
    }
    public void setQuantidadeProduzida(int quantidadeProduzida) {
        this.quantidadeProduzida = quantidadeProduzida;
    }
}

class Horista extends Funcionario {
    private float valorHora;
    private int totalHoras;

    public Horista() {}
    public Horista(String nome, String dataNascimento, float salario, float valorHora, int totalHoras) {
        super(nome, dataNascimento, salario);
        salario += valorHora*totalHoras;
        super.setSalario(salario);
        this.valorHora = valorHora;
        this.totalHoras = totalHoras;
    }
    public float getValorHora() {
        return valorHora;
    }
    public void setValorHora(float valorHora) {
        this.valorHora = valorHora;
    }
    public int getTotalHoras() {
        return totalHoras;
    }
    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }
}

class FolhaPagamento {
    public void mostrarPagamentos(String nome, String cidade, String mes, Funcionario funcionarios[]) {
        System.out.println("Nome da Empresa: " + nome);
        System.out.println("Endereço: " + cidade);
        System.out.println("Mês: " + mes);

        for(int i=0; i<funcionarios.length; i++) {
            System.out.println();
            System.out.println("Nome: " + funcionarios[i].getNome());
            System.out.println("Data de Nascimento: " + funcionarios[i].getDataNascimento());
            System.out.println("Salário: R$" + funcionarios[i].getSalario());
            if(funcionarios[i] instanceof Chefe) {
                System.out.println("Departamento: " + ((Chefe) funcionarios[i]).getDepartamento());
                System.out.println("Cargo: chefe");
            } else if(funcionarios[i] instanceof Vendedor) {
                System.out.println("Cargo: vendedor");
                System.out.println("Comissão por Venda: R$" + ((Vendedor) funcionarios[i]).getComissaoVenda());
                System.out.println("Número de vendas: " + ((Vendedor) funcionarios[i]).getQuantidadeVendas());
            } else if(funcionarios[i] instanceof Operario) {
                System.out.println("Cargo: Operário");
                System.out.println("Valor por Produção: R$" + ((Operario) funcionarios[i]).getValorProducao());
                System.out.println("Quantidade produzida: " + ((Operario) funcionarios[i]).getQuantidadeProduzida());
            } else {
                System.out.println("Cargo: horista");
                System.out.println("Valor por Hora: R$" + ((Horista) funcionarios[i]).getValorHora());
                System.out.println("Total de horas: " + ((Horista) funcionarios[i]).getTotalHoras());
            }
        }
    }
}

//Classe principal
public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Funcionario[] funcionarios = new Funcionario[4];

        String nome = entrada.next();
        String cidade = entrada.next();
        String mes = entrada.next();

        funcionarios[0] = new Chefe(entrada.next(), entrada.next(), entrada.nextFloat(), entrada.next());
        funcionarios[1] = new Vendedor(entrada.next(), entrada.next(), entrada.nextFloat(), entrada.nextFloat(), entrada.nextInt());
        funcionarios[2] = new Operario(entrada.next(), entrada.next(), entrada.nextFloat(), entrada.nextFloat(), entrada.nextInt());
        funcionarios[3] = new Horista(entrada.next(), entrada.next(), entrada.nextFloat(), entrada.nextFloat(), entrada.nextInt());

        FolhaPagamento folha = new FolhaPagamento();
        folha.mostrarPagamentos(nome, cidade, mes, funcionarios);

    }

}
