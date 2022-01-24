package DataBase;

import Exceptions.MaxSizeOvertake;
import HTTPAnswer.Server;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Avaliacao {
    private String id; //key
    private int classificacao;
    private Date data;
    private String texto;
    private String idRestaurante;
    private String usernameCliente;

    public Avaliacao(String id, int classificacao, int ano, int mes, int dia, String texto, String idRestaurante, String usernameCliente) throws MaxSizeOvertake{
        setId(id);
        setTexto(texto);
        setIdRestaurante(idRestaurante);
        setUsernameCliente(usernameCliente);
        this.classificacao = classificacao;
        setData(ano, mes, dia);
    }

    public Avaliacao(int classificacao, int ano, int mes, int dia, String texto, String idRestaurante, String usernameCliente) throws MaxSizeOvertake{
        setId(randomString());
        setTexto(texto);
        setIdRestaurante(idRestaurante);
        setUsernameCliente(usernameCliente);
        this.classificacao = classificacao;
        setData(ano, mes, dia);
    }

    public Avaliacao() {
    }

    public void setData(int ano, int mes, int dia){
        this.data = JDBC.getDateSql(ano, mes, dia);
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

    public String getHTML(){
        return  "<img src=\"https://i.imgur.com/710mXhY.png\" class=\"user_image\">" +
                "<div class=\"flex-child\"> " +
                    "<label> <b>"+usernameCliente+"</b>" +
                        "<p>" + Server.toHTML(texto) + "</p>" +
                        "<p style=\"font-size:25px\">" + htmlStars() + "</p>"+
                    "</label>" +
                "</div>";
    }

    private String htmlStars(){
        return "&#9733".repeat(classificacao)+"&#9734".repeat(5-classificacao);
    }

    public static String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 19;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
