package src.DataBase;

import src.Avaliacao;
import src.Comida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComidaDAO {
    private final Connection con;

    public ComidaDAO(Connection con) {
        this.con = con;
    }

    private PreparedStatement codLine(String str) {
        try {
            return this.con.prepareStatement(str);
        } catch (SQLException e) {
            return null;
        }
    }

    private Comida fromResultSet2Com(ResultSet rs){
        Comida c = new Comida();
        try {
            c.setId(rs.getInt("id"));
            c.setVegetariano(rs.getShort("vegetariano"));
            c.setNome(rs.getString("nome"));
        } catch (SQLException e) {
            return null;
        }
        return c;
    }

    public int addComida(Comida c){
        PreparedStatement ps = codLine("Insert into comida(id,vegetariano,nome) values (?,?,?)");
        if(ps != null){
            try {
                ps.setInt(1,c.getId());
                ps.setShort(2,c.getVegetariano());
                ps.setString(3,c.getNome());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0; //Impossível adicionar à base de dados
            }
            return 1; //Sucesso ao adiconar a Avaliação
        }
        return -1; //Impossível fazer ligação com a base de dados
    }


}
