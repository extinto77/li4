package src;

import java.util.Date;

public class Avaliacao {
    private String id; //key
    private int classificacao;
    private Date data;
    private String texto;
    private String idRestaurante;
    private String usernameCliente;

    public Avaliacao() {
    }

    public String getId() {
        return id;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public Date getData() {
        return data;
    }

    public String getTexto() {
        return texto;
    }

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public String getUsernameCliente() {
        return usernameCliente;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public void setUsernameCliente(String usernameCliente) {
        this.usernameCliente = usernameCliente;
    }
}
