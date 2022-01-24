package DataBase;

import Exceptions.*;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

public class RestauranteDAO {
    private final Connection con;

    public RestauranteDAO(Connection con){
        this.con = con;
    }

    private Restaurante fromResultSet2Rest(ResultSet rs){
        Restaurante r = new Restaurante();
        try {
            r.setPaisOrigem(rs.getString("paisOrigem"));
            r.setLocalidade(rs.getString("localidade"));
            r.setRua(rs.getString("rua"));
            r.setGps(rs.getString("gps"));
            r.setPreco(rs.getString("preco"));
            r.setNome(rs.getString("nome"));
            r.setTelefone(rs.getString("telefone"));
            r.setId(rs.getString("id"));
        } catch (SQLException | MaxSizeOvertake | InvalidFormat e) {
            return null;
        }
        return r;
    }

    private String float2String(float fl){
        int inteiro = (int) fl;
        int decimal = (int)((fl-inteiro)*100);
        String str = inteiro +",";
        if(decimal==0) str += "00€";
        else if(decimal<10) str += "0"+decimal+"€";
        else str += decimal+"€";
        return str;
    }

    public int addRestaurante(Restaurante r) throws BDFailedConnection, AddingError {
        PreparedStatement ps = JDBC.codLine(this.con, "Insert into restaurante(paisOrigem,localidade,rua,gps,preco,nome,telefone,id) values (?,?,?,?,?,?,?,?)");
        if(ps != null){
            try {
                ps.setString(1,r.getPaisOrigem());
                ps.setString(2,r.getLocalidade());
                ps.setString(3,r.getRua());
                ps.setString(4,r.getGps());
                ps.setString(5,float2String(r.getPreco()));
                ps.setString(6,r.getNome());
                ps.setString(7,""+r.getTelefone());
                ps.setString(8,r.getId());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new AddingError(); //Impossível adicionar à base de dados
            }
            return 1; //Sucesso ao adiconar a Avaliação
        }
        throw new BDFailedConnection(); //Impossível fazer ligação com a base de dados
    }

    Restaurante getByIdRestaurante(String id) throws BDFailedConnection {
        PreparedStatement ps = JDBC.codLine(this.con, "SELECT * FROM restaurante WHERE id='"+id+"'"); // ver se é "... 'id'=" e na de baixo também
        if(ps != null){
            try {
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return fromResultSet2Rest(rs);
                }
                else throw new NoMatch();
            }
            catch (SQLException | NoMatch e){
                return null;
            }
        }
        throw new BDFailedConnection();
    }

    public List<Restaurante> getRestaurantesByNacionalidade(String nacionalidade) throws BDFailedConnection {
        PreparedStatement ps = JDBC.codLine(this.con, "SELECT * FROM restaurante WHERE nacionalidade='"+nacionalidade+"'"); // rever

        List<Restaurante> list = new ArrayList<>();
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                String lower_nacionalidade = nacionalidade.toLowerCase();
                while (rs.next()) {
                    Restaurante r = fromResultSet2Rest(rs);
                    if (r!=null && r.getPaisOrigem().toLowerCase().equals(lower_nacionalidade))
                    list.add(r);
                }
            }catch (SQLException e) {
                return null;
            }
            return list;
        }
        throw new BDFailedConnection();
    }

    public List<Restaurante> getAllRestaurantesNome(String nome) throws BDFailedConnection {
        PreparedStatement ps = JDBC.codLine(this.con, "SELECT * FROM restaurante");

        List<Restaurante> list = new ArrayList<>();
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                String lower_nome = nome.toLowerCase();
                while (rs.next()) {
                    Restaurante r = fromResultSet2Rest(rs);
                    if(r != null){
                        String resNome = r.getNome().toLowerCase();
                        if(lower_nome.matches("(.*)"+ resNome+"(.*)"))
                            list.add(r);
                    }
                }
            }catch (SQLException e) {
                return null;
            }
            return list;
        }
        throw new BDFailedConnection();
    }

    public List<Restaurante> getRestaurantesPreco(int min, int max) throws BDFailedConnection {
        PreparedStatement ps = JDBC.codLine(this.con, "SELECT * FROM restaurante");

        List<Restaurante> list = new ArrayList<>();
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Restaurante r = fromResultSet2Rest(rs);
                    if(r != null){
                        float preco = r.getPreco();
                        if(min<=preco && preco<=max)
                            list.add(r);
                    }
                }
            }catch (SQLException e) {
                return null;
            }
            return list;
        }
        throw new BDFailedConnection();
    }

    public List<Restaurante> getAllRestaurantes() throws BDFailedConnection {
        PreparedStatement ps = JDBC.codLine(this.con, "SELECT * FROM restaurante");

        List<Restaurante> list = new ArrayList<>();
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Restaurante r = fromResultSet2Rest(rs);
                    if (r!=null)
                        list.add(r);
                }
            }catch (SQLException e) {
                return null;
            }
            return list;
        }
        throw new BDFailedConnection();
    }

    public Restaurante getRandomRestaurante() throws BDFailedConnection {
        List<Restaurante> list = getAllRestaurantes();
        int size = list.size();
        Random rand = new Random();
        int index = rand.nextInt(size);
        return list.get(index);
    }

    public String getAllCoordenates() throws BDFailedConnection{
        List<Restaurante> list = getAllRestaurantes();
        String ret ="";
        int i = 0;
        for(Restaurante r : list){
            ret += "pin[" + i +"] = new Microsoft.Maps.Pushpin(location("+ r.getGps()+"), {\n" +
                    "                    title:" + r.getNome() + ";\n" ;
            i++;
        }
        return ret;
    }

    public Restaurante getMaisProximo(String gpsAtual) throws BDFailedConnection{
        PreparedStatement ps = JDBC.codLine(this.con, "SELECT * FROM restaurante");
        Restaurante resFinal = null;
        double disMin = 999999999.0;
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                Restaurante r;
                while (rs.next()) {
                    r = fromResultSet2Rest(rs);
                    if (r != null){
                        double dis = calculaDistancia(gpsAtual, r.getGps());
                        if(dis<disMin) {
                            resFinal=r;
                            disMin=dis;
                        }
                    }
                }
            }catch (SQLException e) {
                return null;
            }
            return resFinal;
        }
        else throw new BDFailedConnection();
    }

    private static double getCoor(String str1, String str2){
        double val = Integer.parseInt(str1);
        double aux = Integer.parseInt(str2);
        int size = str2.length();
        double aux2 = Math.pow(10,size);
        if (val<0) val -= aux/aux2;
        else val += aux/aux2;
        return val;
    }

    private static double calculaDistancia(String inicio, String fim){
        // "41,529383, -8,446513";
        double EARTH_RADIUS_KM = 6371.0;

        String[] ins = inicio.split(",");
        String[] fns = fim.split(",");

        double latI = getCoor(ins[0], ins[1]);
        double longI = getCoor(ins[2].trim(), ins[3]);
        double latF = getCoor(fns[0], fns[1]);
        double longF = getCoor(fns[2].trim(), fns[3]);

        double ILatToRad = Math.toRadians(latI);
        double FLatToRad = Math.toRadians(latF);
        double deltaLongitudeInRad = Math.toRadians(longF- longI);

        return Math.acos(Math.cos(ILatToRad) * Math.cos(FLatToRad)
                * Math.cos(deltaLongitudeInRad) + Math.sin(ILatToRad)
                * Math.sin(FLatToRad))
                * EARTH_RADIUS_KM;

    }


    public static void main(String[] args){
        double val = calculaDistancia("41,529383, -8,446513", "41,562053, -8,420443");
        System.out.println(val);
    }

}
