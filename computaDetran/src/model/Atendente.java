package model;

public class Atendente extends Pessoa {
    private int numeroIdentificacao;
    private String senhaHash;

    //Construtor vazio
    public Atendente() {}

    //Construtor para SELECT com ID
    public Atendente(int id, String nome, String cpf, String dataNascimento, String endereco, String email, String celular, int numeroIdentificacao, String senhaHash) {
        super(id, nome, cpf, dataNascimento, endereco, email, celular);
        this.numeroIdentificacao = numeroIdentificacao;
        this.senhaHash = senhaHash;
    }

    //Construtor para INSERT sem ID
    public Atendente(String nome, String cpf, String dataNascimento, String endereco, String email, String celular, int numeroIdentificacao, String senhaHash) {
        super(nome, cpf, dataNascimento, endereco, email, celular);
        this.numeroIdentificacao = numeroIdentificacao;
        this.senhaHash = senhaHash;
    }

    //Getters
    public int getNumeroIdentificacao() {return numeroIdentificacao;}
    public String getSenhaHash() {return senhaHash;}

    //Setters
    public void setNumeroIdentificacao(int numeroIdentificacao) {this.numeroIdentificacao = numeroIdentificacao;}
    public void setSenhaHash(String senhaHash) {this.senhaHash = senhaHash;}

    @Override
    public String toString() {
        return "Atendente{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", numeroIdentificacao=" + numeroIdentificacao +
                '}';
    }
}
