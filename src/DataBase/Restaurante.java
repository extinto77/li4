package DataBase;

import Exceptions.InvalidFormat;
import Exceptions.MaxSizeOvertake;

import java.util.Date;

public class Restaurante {
    private String paisOrigem;
    private String localidade;
    private String rua;
    private String gps;
    private float preco;
    private String nome;
    private int telefone;
    private String id; // primary key

    public Restaurante() {
    }

    public Restaurante(String paisOrigem, String localidade, String rua, String gps, String preco, String nome, String telefone, String id) throws MaxSizeOvertake, InvalidFormat {
        setPaisOrigem(paisOrigem);
        setLocalidade(localidade);
        setRua(rua);
        setGps(gps);
        setPreco(preco);
        setNome(nome);
        setTelefone(telefone);
        setId(id);
    }

    private float toNum(String preco) throws InvalidFormat { //"XXX,XX€"
        String[] nums = preco.split(",");
        int dec, in;
        if(nums.length == 2){
            in = Integer.parseInt(nums[0]);
            String[]decs = nums[1].split("€");
            if (decs.length==1 && decs[0].length()==2){
                dec = Integer.parseInt(decs[0]);
            }
            else throw new InvalidFormat();
            return (float) (in+(dec / 100.0));
        }
        else throw new InvalidFormat();
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
        return this.gps;
    }

    public void setGps(String gps) throws MaxSizeOvertake {
        int MAX_SIZE = 100;
        if (gps.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.gps = gps;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(String preco) throws MaxSizeOvertake, InvalidFormat {
        int MAX_SIZE = 7;
        if (preco.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.preco = toNum(preco);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws MaxSizeOvertake {
        int MAX_SIZE = 45;
        if (nome.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.nome = nome;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) throws MaxSizeOvertake, InvalidFormat {
        int MAX_SIZE = 9;
        if (telefone.length()> MAX_SIZE) throw new MaxSizeOvertake();
        int aux = Integer.parseInt(telefone);
        if(aux<201000000|| aux>999999999) throw new InvalidFormat();
        this.telefone = aux;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws MaxSizeOvertake {
        int MAX_SIZE = 9;
        if (id.length()> MAX_SIZE) throw new MaxSizeOvertake();
        this.id = id;
    }

    public String getDescricao(){
        String res="";
        res+="O nosso restaurante está situado na rua: "+getRua()+" no concelho de "+getLocalidade()+".<br>"+
                "Especializamo-nos em comida de origem "+getNacionalidade()+".<br><br>";
        res+="Contacto:</br>&emsp;Telefone:"+getTelefone();
        return res;
    }

    private String getNacionalidade(){
        switch(paisOrigem.toLowerCase()){
            case "china":
                return "Chinesa";
            case "italia":
                return "Italiana";
            case "turquia":
                return "Turca";
            case "japao":
                return "Japonesa";
            case "espanha":
                return "Espanhola";
            case "mexico":
                return "Mexicana";
            default:
                return "";
        }
    }
    public String flagURL(){
        switch(paisOrigem.toLowerCase()){
            case "china":
                return "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Flag_of_the_People%27s_Republic_of_China.svg/125px-Flag_of_the_People%27s_Republic_of_China.svg.png";
            case "italia":
                return "https://upload.wikimedia.org/wikipedia/en/thumb/0/03/Flag_of_Italy.svg/125px-Flag_of_Italy.svg.png";
            case "turquia":
                return "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Flag_of_Turkey.svg/125px-Flag_of_Turkey.svg.png";
            case "japao":
                return "https://upload.wikimedia.org/wikipedia/en/thumb/9/9e/Flag_of_Japan.svg/125px-Flag_of_Japan.svg.png";
            case "espanha":
                return "https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Bandera_de_España.svg/125px-Bandera_de_España.svg.png";
            case "mexico":
                return "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Flag_of_Mexico.svg/125px-Flag_of_Mexico.svg.png";
            default:
                return "";
        }
    }
}
