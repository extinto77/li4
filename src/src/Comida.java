package src;

public class Comida {
    private int id;
    private short vegetariano;
    private String nome;

    public Comida(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getVegetariano() {
        return vegetariano;
    }

    public void setVegetariano(short vegetariano) {
        this.vegetariano = vegetariano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
