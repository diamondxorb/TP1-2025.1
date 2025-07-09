package model;

public class Veiculo {
    private int id;
    private String placa;
    private String modelo;
    private Proprietario proprietario;
    private boolean documentoPago;

    //Construtor vazio
    public Veiculo() {}

    //Construtor para SELECT com ID
    public Veiculo(int id, String placa, String modelo, Proprietario proprietario) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.proprietario = proprietario;
    }

    //Construtor para INSERT sem ID
    public Veiculo(String placa, String modelo, Proprietario proprietario) {
        this.placa = placa;
        this.modelo = modelo;
        this.proprietario = proprietario;
    }


    //Getters
    public int getId() {return id;}
    public String getPlaca() {return placa;}
    public String getModelo() {return modelo;}
    public Proprietario getProprietario() {return proprietario;}
    public boolean isDocumentoPago() {return documentoPago;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setPlaca(String placa) {this.placa = placa;}
    public void setModelo(String modelo) {this.modelo = modelo;}
    public void setProprietario(Proprietario proprietario) {this.proprietario = proprietario;}
    public void setDocumentoPago(boolean documentoPago) {this.documentoPago = documentoPago;}
}