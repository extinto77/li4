package DataBase;

import Exceptions.InvalidFormat;
import Exceptions.MaxSizeOvertake;

public class Comida {
    private int id;
    private boolean vegetariano;
    private String nome;

    public Comida(){

    }

    public Comida(int id, short vegetariano, String nome) throws MaxSizeOvertake, InvalidFormat {
        setNome(nome);
        setId(id);
        setVegetariano(vegetariano);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getVegetariano() {
        return vegetariano;
    }

    public void setVegetariano(short vegetariano) throws InvalidFormat {
        if (vegetariano == (short)0) this.vegetariano=false;
        else if(vegetariano==(short)1) this.vegetariano = true;
        else throw new InvalidFormat();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws MaxSizeOvertake {
        int MAX_SIZE = 45;
        if (nome.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.nome = nome;
    }
}
