package src.DataBase;

import src.Avaliacao;
import src.Restaurante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RestauranteDAO {
    private final Connection con;

    public RestauranteDAO(Connection con){
        this.con = con;
    }

    private PreparedStatement codLine(String str){
        try {
            return this.con.prepareStatement(str);
        }
        catch (SQLException e){
            return null;
        }
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
        } catch (SQLException e) {
            return null;
        }
        return r;
    }

    public int addRestaurante(Restaurante r){
        PreparedStatement ps = codLine("Insert into restaurante(paisOrigem,localidade,rua,gps,preco,nome,telefone,id) values (?,?,?,?,?,?,?,?)");
        if(ps != null){
            try {
                ps.setString(1,r.getPaisOrigem());
                ps.setString(2,r.getLocalidade());
                ps.setString(3,r.getRua());
                ps.setString(4,r.getGps());
                ps.setString(5,r.getPreco());
                ps.setString(6,r.getNome());
                ps.setString(7,r.getTelefone());
                ps.setString(8,r.getId());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0; //Impossível adicionar à base de dados
            }
            return 1; //Sucesso ao adiconar a Avaliação
        }
        return -1; //Impossível fazer ligação com a base de dados
    }

    Restaurante getByIdRestaurante(String id){
        PreparedStatement ps = codLine("SELECT * FROM restaurante WHERE id="+id); // ver se é "... 'id'=" e na de baixo também
        if(ps != null){
            try {
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return fromResultSet2Rest(rs);
                }
                else return null;
            }
            catch (SQLException e){
                return null;
            }
        }
        return null;
    }

    public List<Restaurante> getRestaurantesByNacionalidade(String nacionalidade) {
        PreparedStatement ps = codLine("SELECT * FROM avaliacao WHERE nacionalidade=" + nacionalidade); // rever

        List<Restaurante> list = new ArrayList<>();
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Restaurante r = fromResultSet2Rest(rs);
                    list.add(r);
                }
            }catch (SQLException e) {
                return null;
            }
            return list;
        }
        return null;
    }

    public List<Restaurante> getAllRestaurantes() {
        PreparedStatement ps = codLine("SELECT * FROM avaliacao");

        List<Restaurante> list = new ArrayList<>();
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Restaurante r = fromResultSet2Rest(rs);
                    list.add(r);
                }
            }catch (SQLException e) {
                return null;
            }
            return list;
        }
        return null;
    }
    /* RANDOM
    int size = list.size();
    Random rand = new Random();
    int index = rand.nextInt(size);
    return list.get(index);
    */






}
