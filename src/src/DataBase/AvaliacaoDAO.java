package src.DataBase;

import src.Avaliacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {
    private final Connection con;

    public AvaliacaoDAO(Connection con){
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

    private Avaliacao fromResultSet2Aval(ResultSet rs){
        Avaliacao a = new Avaliacao();
        try {
            a.setId(rs.getString("id"));
            a.setClassificacao(rs.getInt("classificacao"));
            a.setData(rs.getDate("data"));
            a.setTexto(rs.getString("texto"));
            a.setIdRestaurante(rs.getString("idRestaurante"));
            a.setUsernameCliente(rs.getString("usernameCliente"));
        } catch (SQLException e) {
            return null;
        }
        return a;
    }

    public int addAvaliacao(Avaliacao a){
        PreparedStatement ps = codLine("Insert into avaliacao(id,classificacao,data,texto,idRestaurante,usernameCliente) values (?,?,?,?,?,?)");
        if(ps != null){
            try {
                ps.setString(1,a.getId());
                ps.setInt(2,a.getClassificacao());
                ps.setDate(3,new java.sql.Date(a.getData().getTime())); //rever
                ps.setString(4,a.getTexto());
                ps.setString(5,a.getIdRestaurante());
                ps.setString(6,a.getUsernameCliente());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0; //Impossível adicionar à base de dados
            }
            return 1; //Sucesso ao adiconar a Avaliação
        }
        return -1; //Impossível fazer ligação com a base de dados
    }

    Avaliacao getByIdAvaliacao(String id){
        PreparedStatement ps = codLine("SELECT * FROM avaliacao WHERE id="+id); // ver se é "... 'id'=" e na de baixo também
        if(ps != null){
            try {
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return fromResultSet2Aval(rs);
                }
                else return null;
            }
            catch (SQLException e){
                return null;
            }
        }
        return null;
    }

    public List<Avaliacao> getAvaliacoes(String flag, String id) {
        if (!(flag.equals("restaurante") || flag.equals("username"))) return null;
        PreparedStatement ps = codLine("SELECT * FROM avaliacao WHERE " + flag + "=" + id);
        List<Avaliacao> list = new ArrayList<>();
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Avaliacao a = fromResultSet2Aval(rs);
                    list.add(a);
                }
            }catch (SQLException e) {
                return null;
            }
            return list;
        }
        return null;
    }


}
