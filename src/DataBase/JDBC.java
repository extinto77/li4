package DataBase;

import Exceptions.AddingError;
import Exceptions.BDFailedConnection;
import Exceptions.InvalidFormat;
import Exceptions.MaxSizeOvertake;

import java.sql.*;

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

            Comida comida = new Comida(12345, (short)0, "bigmac");
            Avaliacao avaliacao = new Avaliacao("arturNoMc", 3, new java.sql.Date(System.currentTimeMillis()), "estava bom o bigmac", "Mc", "artur123");
            Cliente cliente = new Cliente("artur123", new java.sql.Date(System.currentTimeMillis()), "artur", "artur456@gmail.com", "969696969", "arturito");
            Restaurante restaurante = new Restaurante("usa", "braga", "rua dos restaurantes", "14ºN,23ºE", "8,00€", "mcdonalds", "253253253", "Mc");


            if(com.addComida(comida)==1) System.out.println("comida add ducesso");
            if(ava.addAvaliacao(avaliacao)==1)System.out.println("avaliacao add ducesso");
            if(cli.addCliente(cliente)==1)System.out.println("cliente add ducesso");
            if(res.addRestaurante(restaurante)==1)System.out.println("restaurante add ducesso");

            if(cli.deleteCliente(cliente.getUsername())==1) System.out.println("sucesso a eliminar user");

        } catch (SQLException | MaxSizeOvertake | AddingError | BDFailedConnection | InvalidFormat throwables) {
            throwables.printStackTrace();
        }
    }
}