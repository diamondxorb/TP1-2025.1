package model;

public class Proprietario extends Pessoa {
    private String senha;
    private int documentoVeiculo;
    private boolean isPago;

    //Construtor vazio
    public Proprietario() {}

    //Construtor para SELECT com ID
    public Proprietario(int id,String nome, String cpf, String dataDeNascimento, String endereco, String email, String celular, String senha) {
        super(id ,nome, cpf, dataDeNascimento, endereco, email, celular);
        this.senha = senha;
    }

    //Construtor para INSERT sem ID
    public Proprietario(String nome, String cpf, String dataDeNascimento, String endereco, String email, String celular, String senha) {
        super(nome, cpf, dataDeNascimento, endereco, email, celular);
        this.senha = senha;
    }

    //Getters
    public String getSenha() {return senha;}
    public int getDocumentoVeiculo() {return documentoVeiculo;}
    public boolean isPago() {return isPago;}

    //Setters
    public void setSenha(String senha) {this.senha = senha;}
    public void setDocumentoVeiculo(int documentoVeiculo) {this.documentoVeiculo = documentoVeiculo;}
    public void setPago(boolean isPago) {this.isPago = isPago;}
}
