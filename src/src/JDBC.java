package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306";
        String user = "besta";
        String password = "bestasmei1921";
        String query = "cenas";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(query);

            while (res.next()){
                
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
