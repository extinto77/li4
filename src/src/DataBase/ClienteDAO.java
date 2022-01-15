package src.DataBase;

import src.Avaliacao;
import src.Cliente;
import src.Exceptions.BDFailedConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final Connection con;

    public ClienteDAO(Connection con){
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

    private Cliente fromResultSet2Cli(ResultSet rs){
        Cliente c = new Cliente();
        try {
            c.setUsername(rs.getString("username"));
            c.setDataNascimento(rs.getDate("dataNascimento"));
            c.setNome(rs.getString("data"));
            c.setEmail(rs.getString("email"));
            c.setTelemovel(rs.getString("telemovel"));
            c.setPassword(rs.getString("password"));
        } catch (SQLException e) {
            return null;
        }
        return c;
    }

    public int addCliente(Cliente c) throws BDFailedConnection {
        PreparedStatement ps = codLine("Insert into cliente(username,dataNascimento,nome,email,telemovel,password) values (?,?,?,?,?,?)");
        if(ps != null){
            try {
                ps.setString(1, c.getUsername());
                ps.setDate(2, new java.sql.Date(c.getDataNascimento().getTime()));//rever
                ps.setString(3, c.getNome());
                ps.setString(4, c.getEmail());
                ps.setString(5, c.getTelemovel());
                ps.setString(6, c.getPassword());
                ps.executeUpdate();
            } catch (SQLException e) {
                return 0; //Impossível adicionar à base de dados
            }
            return 1; //Sucesso ao adiconar a Avaliação
        }
        throw new BDFailedConnection(); //Impossível fazer ligação com a base de dados
    }

    Cliente getByUsername(String username) throws BDFailedConnection {
        PreparedStatement ps = codLine("SELECT * FROM cliente WHERE username="+username); // ver se é "... 'username'=" e na de baixo também
        if(ps != null){
            try {
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return fromResultSet2Cli(rs);
                }
                else return null;
            }
            catch (SQLException e){
                return null;
            }
        }
        throw new BDFailedConnection();
    }

}
