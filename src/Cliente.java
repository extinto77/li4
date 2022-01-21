import Exceptions.InvalidFormat;
import Exceptions.MaxSizeOvertake;

import java.util.Date;

public class Cliente {
    private String username;
    private Date dataNascimento;
    private String nome;
    private String email;
    private int telemovel;
    private String password;

    // fazer hash para a password

    public Cliente(){}

    public Cliente(String username, Date dataNascimento, String nome, String email, String telemovel, String password) throws MaxSizeOvertake, InvalidFormat {
        setUsername(username);
        setNome(nome);
        setEmail(email);
        setTelemovel(telemovel);
        setPassword(password);
        this.dataNascimento = dataNascimento;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws MaxSizeOvertake {
        int MAX_SIZE = 20;
        if (username.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.username = username;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws MaxSizeOvertake {
        int MAX_SIZE = 90;
        if (nome.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws MaxSizeOvertake {
        int MAX_SIZE = 100;
        if (email.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.email = email;
    }

    public int getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) throws MaxSizeOvertake, InvalidFormat {
        int MAX_SIZE = 9;
        if (telemovel.length()> MAX_SIZE) throw new MaxSizeOvertake();
        int aux = Integer.parseInt(telemovel);
        if (aux<900000000 || aux>999999999 )throw new InvalidFormat();
        this.telemovel = aux;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws MaxSizeOvertake {
        int MAX_SIZE = 25;
        if (password.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.password = password;
    }
}
