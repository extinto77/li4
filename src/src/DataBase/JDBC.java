package src.DataBase;
import java.sql.*;

public class JDBC {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/li4";
        String user = "besta";
        String password = "bestasmei1921";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            // passar este con para a controler

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
