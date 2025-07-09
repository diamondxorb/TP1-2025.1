package model;

public class Agendamento {
    private int id;
    private String data;
    private String horario;
    private String motivoAgendamento;
    private Atendente atendente;
    private Vistoriador vistoriador;
    private Veiculo veiculo;

    private Laudo laudo;
    private String status;

    //Construtor vazio
    public Agendamento() {}

    //Construtor para SELECT com ID
    public Agendamento(int id, String data, String horario, String motivoAgendamento, Atendente atendente, Vistoriador vistoriador) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.motivoAgendamento = motivoAgendamento;
        this.atendente = atendente;
        this.vistoriador = vistoriador;
    }

    //Construtor para INSERT sem ID
    public Agendamento(String data, String horario, String motivoAgendamento, Atendente atendente, Vistoriador vistoriador) {
        this.data = data;
        this.horario = horario;
        this.motivoAgendamento = motivoAgendamento;
        this.atendente = atendente;
        this.vistoriador = vistoriador;
    }

    //Getters
    public int getId() {return id;}
    public String getData() {return data;}
    public String getHorario() {return horario;}
    public String getMotivoAgendamento() {return motivoAgendamento;}
    public Atendente getAtendente() {return atendente;}
    public Vistoriador getVistoriador() {return vistoriador;}
    public Veiculo getVeiculo() {return veiculo;}
    public Laudo getLaudo() {return laudo;}
    public String getStatus() {return status;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setData(String data) {this.data = data;}
    public void setHorario(String horario) {this.horario = horario;}
    public void setMotivoAgendamento(String motivoAgendamento) {this.motivoAgendamento = motivoAgendamento;}
    public void setAtendente(Atendente atendente) {this.atendente = atendente;}
    public void setVistoriador(Vistoriador vistoriador) {this.vistoriador = vistoriador;}
    public void setVeiculo(Veiculo veiculo) {this.veiculo = veiculo;}
    public void setLaudo(Laudo laudo) {this.laudo = laudo;}
    public void setStatus(String status) {this.status = status;}
}