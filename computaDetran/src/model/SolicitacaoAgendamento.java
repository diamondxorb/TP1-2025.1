package model;

public class SolicitacaoAgendamento {
    private int id;
    private String status;
    private Veiculo veiculo;
    private String motivoNegacao;

    //Construtor vazio
    public SolicitacaoAgendamento() {}

    //Construtor para SELECT com ID
    public SolicitacaoAgendamento(int id, String status, Veiculo veiculo) {
        this.id = id;
        this.status = status;
        this.veiculo = veiculo;
    }

    //Construtor para INSERT sem ID
    public SolicitacaoAgendamento(String status, Veiculo veiculo) {
        this.status = status;
        this.veiculo = veiculo;
    }

    //Getters
    public int getId() {return id;}
    public String getStatus() {return status;}
    public Veiculo getVeiculo() {return veiculo;}
    public String getMotivoNegacao() {return motivoNegacao;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setStatus(String status) {this.status = status;}
    public void setVeiculo(Veiculo veiculo) {this.veiculo = veiculo;}
    public void setMotivoNegacao(String motivoNegacao) {this.motivoNegacao = motivoNegacao;}
}