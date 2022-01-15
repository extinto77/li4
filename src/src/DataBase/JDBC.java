package src.DataBase;
import src.Avaliacao;
import src.Comida;
import src.Exceptions.AddingError;
import src.Exceptions.BDFailedConnection;
import src.Exceptions.InvalidFormat;
import src.Exceptions.MaxSizeOvertake;

import java.sql.*;
import java.util.Date;

public class JDBC {
    public static PreparedStatement codLine(Connection con, String str){
        try {
            return con.prepareStatement(str);
        }
        catch (SQLException e){
            return null;
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/li4";
        String user = "besta";
        String password = "bestasmei1920";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            Tables tab = new Tables(new AvaliacaoDAO(con), new ClienteDAO(con), new ComidaDAO(con), new RestauranteDAO(con));
            // passar este tab depois

            AvaliacaoDAO ava = tab.getAva();
            ClienteDAO cli = tab.getCli();
            ComidaDAO com = tab.getCom();
            RestauranteDAO res = tab.getRes();

            Comida c = new Comida(12345, (short)0, "Pastel nata");
            Avaliacao a = new Avaliacao("artur", 3, new java.sql.Date(System.currentTimeMillis()), "poc poc poc", "tascaZe", "tobias");
            com.addComida(c);
            ava.addAvaliacao(a);
        } catch (SQLException | MaxSizeOvertake | AddingError | BDFailedConnection | InvalidFormat throwables) {
            throwables.printStackTrace();
        }
    }

}
