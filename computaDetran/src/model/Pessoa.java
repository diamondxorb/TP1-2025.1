package model;

public abstract class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String endereco;
    private String email;
    private String celular;

    //Construtor vazio
    public Pessoa() {}

    //Construtor para SELECT com ID
    public Pessoa(int id, String nome, String cpf, String dataNascimento, String endereco, String email, String celular) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.email = email;
        this.celular = celular;
    }

    //Construtor para INSERT sem ID
    public Pessoa(String nome, String cpf, String dataNascimento, String endereco, String email, String celular) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.email = email;
        this.celular = celular;
    }

    //Getters
    public int getId() {return id;}
    public String getNome() {return nome;}
    public String getCpf() {return cpf;}
    public String getDataNascimento() {return dataNascimento;}
    public String getEndereco() {return endereco;}
    public String getEmail() {return email;}
    public String getCelular() {return celular;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
    public void setCpf(String cpf) {this.cpf = cpf;}
    public void setDataNascimento(String dataNascimento) {this.dataNascimento = dataNascimento;}
    public void setEndereco(String endereco) {this.endereco = endereco;}
    public void setEmail(String email) {this.email = email;}
    public void setCelular(String celular) {this.celular = celular;}
}
