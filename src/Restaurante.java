package src;

import src.Exceptions.MaxSizeOvertake;

import java.util.Date;

public class Restaurante {
    private String paisOrigem;
    private String localidade;
    private String rua;
    private String gps;
    private String preco;
    private String nome;
    private String telefone;
    private String id; // primary key

    public Restaurante() {
    }

    public Restaurante(String paisOrigem, String localidade, String rua, String gps, String preco, String nome, String telefone, String id) throws MaxSizeOvertake {
        setPaisOrigem(paisOrigem);
        setLocalidade(localidade);
        setRua(rua);
        setGps(gps);
        setPreco(preco);
        setNome(nome);
        setTelefone(telefone);
        setId(id);
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) throws MaxSizeOvertake {
        int MAX_SIZE = 45;
        if (paisOrigem.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.paisOrigem = paisOrigem;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) throws MaxSizeOvertake {
        int MAX_SIZE = 90;
        if (localidade.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.localidade = localidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) throws MaxSizeOvertake {
        int MAX_SIZE = 90;
        if (rua.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.rua = rua;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) throws MaxSizeOvertake {
        int MAX_SIZE = 100;
        if (gps.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.gps = gps;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) throws MaxSizeOvertake {
        int MAX_SIZE = 7;
        if (preco.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws MaxSizeOvertake {
        int MAX_SIZE = 45;
        if (nome.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) throws MaxSizeOvertake {
        int MAX_SIZE = 9;
        if (telefone.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws MaxSizeOvertake {
        int MAX_SIZE = 9;
        if (id.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.id = id;
    }
}
