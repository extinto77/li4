package DataBase;

import Exceptions.AddingError;
import Exceptions.BDFailedConnection;
import Exceptions.InvalidFormat;
import Exceptions.MaxSizeOvertake;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JDBC {
    public static PreparedStatement codLine(Connection con, String str){
        try {
            return con.prepareStatement(str);
        }
        catch (SQLException e){
            return null;
        }
    }

    public static boolean existsKey(Connection con, String table, String columnName, String key) throws BDFailedConnection{
        PreparedStatement ps = JDBC.codLine(con, "SELECT * FROM " + table + "WHERE " + columnName + "='" + key + "'"); // ver se é "... 'id'=" e na de baixo também
        if (ps != null) {
            try {
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException ignored) {
                throw new BDFailedConnection();
            }
        }
        else throw new BDFailedConnection();
    }

    public static java.sql.Date getDateSql(int ano, int mes, int dia){
        String sMes = "";
        if (mes<10) sMes = "0";
        sMes += mes;
        String sDia = "";
        if (dia<10) sDia = "0";
        sDia += dia;
        String str = ano+"-"+sMes+"-"+sDia;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(str, formatter);
        return java.sql.Date.valueOf(ld);
    }

    public static Tables iniciaBD() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/li4";
        String user = "besta";
        String password = "bestasmei1920";

        Connection con = DriverManager.getConnection(url, user, password);

        return new Tables(new AvaliacaoDAO(con), new ClienteDAO(con), new ComidaDAO(con), new RestauranteDAO(con));
    }
}