package model;

import java.util.Date;

public class Laudo {
    int id;
    private String status;
    private String motivo;
    private Date dataEmissao;
    private Vistoriador vistoriador;
    private Agendamento agendamento;
    private Veiculo veiculo;

    //Construtor vazio
    public Laudo() {}

    //Construtor para SELECT com ID
    public Laudo(int id, String status, String motivo, Date dataEmissao, Vistoriador vistoriador) {
        this.id = id;
        this.status = status;
        this.motivo = motivo;
        this.dataEmissao = dataEmissao;
        this.vistoriador = vistoriador;
    }

    //Construtor para INSERT sem ID
    public Laudo(String status, String motivo, Date dataEmissao, Vistoriador vistoriador) {
        this.status = status;
        this.motivo = motivo;
        this.dataEmissao = dataEmissao;
        this.vistoriador = vistoriador;
    }

    //Função para formatar a data de emissão
    public String getDataEmissaoFormatada() {
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataEmissao);
    }

    //Getters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getStatus() {return status;}
    public String getMotivo() {return motivo;}
    public Vistoriador getVistoriador() {return vistoriador;}
    public Date getDataEmissao() {return dataEmissao;}
    public Agendamento getAgendamento() {return agendamento;}
    public Veiculo getVeiculo() {return veiculo;}

    //Setters
    public void setStatus(String status) {this.status = status;}
    public void setMotivo(String motivo) {this.motivo = motivo;}
    public void setDataEmissao(Date dataEmissao) {this.dataEmissao = dataEmissao;}
    public void setVistoriador(Vistoriador vistoriador) {this.vistoriador = vistoriador;}
    public void setAgendamento(Agendamento agendamento) {this.agendamento = agendamento;}
    public void setVeiculo(Veiculo veiculo) {this.veiculo = veiculo;}

    //Função para printar no console o laudo
    public void mostrarLaudo() {
        System.out.println("Status: " + this.status);
        System.out.println("Motivo: " + this.motivo);
        System.out.println("Data de emissão: " + this.dataEmissao);
        System.out.println("Vistoriador: " + (this.vistoriador != null ? this.vistoriador.getNome() : "N/A"));
    }
}