package DataBase;

import Exceptions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComidaDAO {
    private final Connection con;

    public ComidaDAO(Connection con) {
        this.con = con;
    }

    private Comida fromResultSet2Com(ResultSet rs){
        Comida c = new Comida();
        try {
            c.setId(rs.getInt("id"));
            c.setVegetariano(rs.getShort("vegetariano"));
            c.setNome(rs.getString("nome"));
        } catch (SQLException | MaxSizeOvertake | InvalidFormat e) {
            return null;
        }
        return c;
    }

        private short boolean2short(boolean b){
        if (b) return (short) 1;
        else return (short) 0;
    }

    public int addComida(Comida c) throws BDFailedConnection, AddingError {
        PreparedStatement ps = JDBC.codLine(this.con, "Insert into comida(vegetariano,nome) values (?,?)");
        if(ps != null){
            try {
                // id autoincrements
                ps.setShort(1,boolean2short(c.getVegetariano()));
                ps.setString(2, c.getNome());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new AddingError(); //Impossível adicionar à base de dados
            }
            return 1; //Sucesso ao adiconar a Avaliação
        }
        throw new BDFailedConnection(); //Impossível fazer ligação com a base de dados
    }

    Comida getById(int id) throws BDFailedConnection, NoMatch {
        PreparedStatement ps = JDBC.codLine(this.con, "SELECT * FROM comida WHERE id='"+id+"'"); // ver se é "... 'username'=" e na de baixo também
        if(ps != null){
            try {
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return fromResultSet2Com(rs);
                }
                else throw new NoMatch();
            }
            catch (SQLException e){
                return null;
            }
        }
        throw new BDFailedConnection();
    }


}
