package model;

//Classe responsável por fazer vistorias a partir dos agendamentos, cria laudos para cada situação
public class Vistoriador extends Pessoa {
    private int cadastroDetran;
    private String assinatura;
    private String senha;

    //Construtor vazio
    public Vistoriador() {}

    //Construtor para SELECT com ID
    public Vistoriador(int id, String nome, String cpf, String dataDeNascimento, String endereco, String email, String celular, int cadastroDetran, String senha, String assinatura) {
        super(id, nome, cpf, dataDeNascimento, endereco, email, celular);
        this.cadastroDetran = cadastroDetran;
        this.senha = senha;
        this.assinatura = assinatura;
    }

    //Construtor para INSERT sem ID
    public Vistoriador(String nome, String cpf, String dataDeNascimento, String endereco, String email, String celular, int cadastroDetran, String senha, String assinatura) {
        super(nome, cpf, dataDeNascimento, endereco, email, celular);
        this.cadastroDetran = cadastroDetran;
        this.senha = senha;
        this.assinatura = assinatura;
    }

    //Getters
    public int getCadastroDetran() {return cadastroDetran;}
    public String getSenha() {return senha;}
    public String getAssinatura() {return assinatura;}

    //Setters
    public void setCadastroDetran(int cadastroDetran) {this.cadastroDetran = cadastroDetran;}
    public void setSenha(String senha) {this.senha = senha;}
    public void setAssinatura(String assinatura) {this.assinatura = assinatura;}
}