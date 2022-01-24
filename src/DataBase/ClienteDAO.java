package DataBase;

import Exceptions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private final Connection con;

    public ClienteDAO(Connection con) {
        this.con = con;
    }

    private Cliente fromResultSet2Cli(ResultSet rs) {
        Cliente c = new Cliente();
        try {
            c.setUsername(rs.getString("username"));
            c.setDataNascimento(rs.getDate("dataNascimento"));
            c.setNome(rs.getString("nome"));
            c.setEmail(rs.getString("email"));
            c.setTelemovel(rs.getString("telemovel"));
            c.setPassword(rs.getString("password"));
        } catch (SQLException | MaxSizeOvertake | InvalidFormat e) {
            return null;
        }
        return c;
    }

    public int addCliente(Cliente c) throws BDFailedConnection, AddingError {
        PreparedStatement ps = JDBC.codLine(this.con,
                "Insert into cliente(username,dataNascimento,nome,email,telemovel,password) values (?,?,?,?,?,?)");
        if (ps != null) {
            try {
                ps.setString(1, c.getUsername());
                ps.setDate(2, new java.sql.Date(c.getDataNascimento().getTime()));// rever
                ps.setString(3, c.getNome());
                ps.setString(4, c.getEmail());
                ps.setString(5, ""+c.getTelemovel());
                ps.setString(6, c.getPassword());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new AddingError(); // Impossível adicionar à base de dados
            }
            return 1; // Sucesso ao adiconar a Avaliação
        }
        throw new BDFailedConnection(); // Impossível fazer ligação com a base de dados
    }

    public Cliente getByField(String username,String field) throws BDFailedConnection, NoMatch {
        PreparedStatement ps = JDBC.codLine(this.con, "SELECT * FROM cliente WHERE "+field+"='"+username+"'"); // ver se é
                                                                                                           // "...
                                                                                                           // 'username'="
                                                                                                           // e na de
                                                                                                           // baixo
                                                                                                           // também
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return fromResultSet2Cli(rs);
                } else
                    throw new NoMatch();
            } catch (SQLException e) {
                return null;
            }
        }
        throw new BDFailedConnection();
    }

    public Cliente changeInfo(String username, String newUsername, String nome, String email, String password)
            throws BDFailedConnection, NoMatch, AddingError {
        if (newUsername == null && nome == null && email == null && password == null)
            return null;
        boolean first = true;
        String command = "UPDATE cliente SET ";
        if (newUsername != null) {
            command += ("username = '" + newUsername + "'");
            first = false;
        }
        if (nome != null) {
            if (!first)
                command += " , ";
            command += ("nome = '" + nome + "'");
            first = false;
        }
        if (email != null) {
            if (!first)
                command += " , ";
            command += ("email = '" + email + "'");
            first = false;
        }
        if (password != null) {
            if (!first)
                command += " , ";
            command += ("password = '" + password + "'");
        }
        command += " WHERE username = '" + username + "'";

        PreparedStatement ps = JDBC.codLine(this.con, command); // ver se é "... 'username'=" e na de baixo também
        if (ps != null) {
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new AddingError();
            }
        }
        throw new BDFailedConnection();
    }

    public int deleteCliente(String username) throws BDFailedConnection, AddingError {
        PreparedStatement ps = JDBC.codLine(this.con, "DELETE FROM cliente WHERE username='"+username+"'");
        if(ps != null){
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new AddingError(); // Impossível adicionar à base de dados
            }
            return 1; // Sucesso ao adiconar a Avaliação
        }
        throw new BDFailedConnection(); // Impossível fazer ligação com a base de dados
    }

    public int existsOnBD(String email, String numero, String user) throws BDFailedConnection{
        if (JDBC.existsKey(this.con, "cliente", "username", user)) return 1;
        if (JDBC.existsKey(this.con, "cliente", "email", email)) return 2;
        if (JDBC.existsKey(this.con, "cliente", "telemovel", numero)) return 3;
        else return 0;
    }
}
