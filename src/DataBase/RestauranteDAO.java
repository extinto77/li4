package src.DataBase;

import src.Exceptions.*;
import src.Restaurante;

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


    public static void main(String[] args) throws InvalidFormat {
        /*String url = "jdbc:mysql://localhost:3306/li4";
        String user = "besta";
        String password = "bestasmei1920";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            RestauranteDAO resdao = new RestauranteDAO(con);
            System.out.println(resdao.toNum("20,34€"));
            System.out.println(resdao.toNum("020,34€"));
            System.out.println(resdao.toNum("020,04€"));
            //System.out.println(resdao.toNum("020,3€"));
            System.out.println(resdao.toNum("020,30€"));
            // passar este con para a controler

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        String str = "1cenas";
        System.out.println(str.matches("(.*)cenas"));
        System.out.println(str.matches("cenas(.*)"));
        System.out.println(str.matches("(.*)cenas(.*)"));
    }

}
