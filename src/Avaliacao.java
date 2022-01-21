package src;

import src.Exceptions.MaxSizeOvertake;

import java.util.Date;

public class Avaliacao {
    private String id; //key
    private int classificacao;
    private Date data;
    private String texto;
    private String idRestaurante;
    private String usernameCliente;

    public Avaliacao(String id, int classificacao, Date data, String texto, String idRestaurante, String usernameCliente) throws MaxSizeOvertake{
        setId(id);
        setTexto(texto);
        setIdRestaurante(idRestaurante);
        setUsernameCliente(usernameCliente);
        this.classificacao = classificacao;
        this.data = data;
    }

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

    public void setId(String id) throws MaxSizeOvertake {
        int MAX_SIZE = 20;
        if (id.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.id = id;
    }

    public void setClassificacao(int classificacao){
        this.classificacao = classificacao;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTexto(String texto) throws MaxSizeOvertake {
        int MAX_SIZE = 250;
        if (texto.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.texto = texto;
    }

    public void setIdRestaurante(String idRestaurante) throws MaxSizeOvertake {
        int MAX_SIZE = 9;
        if (idRestaurante.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.idRestaurante = idRestaurante;
    }

    public void setUsernameCliente(String usernameCliente) throws MaxSizeOvertake {
        int MAX_SIZE = 14;
        if (usernameCliente.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.usernameCliente = usernameCliente;
    }
}
