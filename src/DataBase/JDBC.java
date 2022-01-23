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

            /*Comida comida = new Comida(12345, (short)0, "bigmac");
            Avaliacao avaliacao = new Avaliacao("arturNoMc", 3, new java.sql.Date(System.currentTimeMillis()), "estava bom o bigmac", "Mc", "artur123");
            Cliente cliente = new Cliente("artur123", new java.sql.Date(System.currentTimeMillis()), "artur", "artur456@gmail.com", "969696969", "arturito");
            Restaurante restaurante = new Restaurante("usa", "braga", "rua dos restaurantes", "14ºN,23ºE", "8,00€", "mcdonalds", "253253253", "Mc");


            if(com.addComida(comida)==1) System.out.println("comida add ducesso");
            if(ava.addAvaliacao(avaliacao)==1)System.out.println("avaliacao add ducesso");
            if(cli.addCliente(cliente)==1)System.out.println("cliente add ducesso");
            if(res.addRestaurante(restaurante)==1)System.out.println("restaurante add ducesso");

            if(cli.deleteCliente(cliente.getUsername())==1) System.out.println("sucesso a eliminar user");
            */
            //chineses
            /*Restaurante asiaWok = new Restaurante("china", "braga", "Rua Damiana Maria da Silva 14", "41,529383, -8,446513", "8,50€", "Asia Wok", "253888888", "asiwok");
            res.addRestaurante(asiaWok);
            Restaurante estrelasMar = new Restaurante("china", "braga", "Praca Ricardo da Rocha, 12", "41,548468, -8,404172", "9,50€", "Restaurante Estrelas do Mar", "253251621", "estrlmar");
            res.addRestaurante(estrelasMar);
            Restaurante grandeMuralha = new Restaurante("china", "braga", "Avenida Padre Julio Fragata, 106", "41,555558, -8,406577", "19,00€", "Grande Muralha", "253781490", "grtWall");
            res.addRestaurante(grandeMuralha);
            Restaurante lisan = new Restaurante("china", "vila verde", "Praça da República, 95", "41,513587, -7,99397", "7,00€", "Lisan", "253221672", "lisan");
            res.addRestaurante(lisan);
            Restaurante braga999 = new Restaurante("china", "braga", "Rua Cidade do Porto, 81", "41,537905, -8,434595", "5,50€", "Braga 999", "964928973", "braga999");
            res.addRestaurante(braga999);
            Restaurante rstAsia = new Restaurante("china", "barcelos", "Rua Bartolomeu Dias, 35", "41,393412, -8,51863", "8,00€", "Rstaurante Asia", "253818311", "rstAsia");
            res.addRestaurante(rstAsia);
            Restaurante fuHao = new Restaurante("china", "barcelos", "Rua Santa Marta, 246", "41,535014, -8,609664", "7,00€", "Restaurante Chines Fu Hao", "253817002", "fuhao");
            res.addRestaurante(fuHao);
            Restaurante nrtChina = new Restaurante("china", "braga", "Rua de Sao Vicente, 151", "41,555321, -8,422202", "15,00€", "O Norte da China", "253616218", "nrtchina");
            res.addRestaurante(nrtChina);
            Restaurante sxiKing = new Restaurante("china", "famalicao", "Rua Ana Placido, 46 Loja 1", "41,412412, -8,518639", "13,00€", "Sushi King", "252217286", "suxiking");
            res.addRestaurante(sxiKing);
            Restaurante mikado = new Restaurante("china", "famalicao", "Avenida dos Descobrimentos, 840", "41,400024, -8,516623", "18,00€", "Mikado", "912503308", "mikado");
            res.addRestaurante(mikado);
            System.out.println("consegui criar restaurantes chineses");

            //mexicanos
            Restaurante toqueria = new Restaurante("mexico", "braga", "Rua dr. Rocha Peixoto, 111", "41,546708, -8,429583", "15,00€", "Taquería El Cascabel", "253170412", "cascabel");
            res.addRestaurante(toqueria);
            Restaurante habanero = new Restaurante("mexico", "braga", "Rua Antonio Marinho, 10", "41,558545, -8,419572", "10,50€", "Habanero Restaurante & Bar Mexicano", "253467357", "habanero");
            res.addRestaurante(habanero);
            System.out.println("consegui criar restaurantes mexicanos");


            //italianos
            Restaurante venezia = new Restaurante("italia", "braga", "Rua José Maria Ottoni, 21", "41,553464, -8,396087", "10,00€", "Pizzaria Bella Venezia", "253679240", "venezia");
            res.addRestaurante(venezia);
            Restaurante mammaMia = new Restaurante("italia", "braga", "Praceta Amândio Ferreira Pinto, 8", "41,568112, -8,386362", "9,00€", "Ristorante Pizzeria Mamma Mia", "253281232", "mammamia");
            res.addRestaurante(mammaMia);
            Restaurante ilfiume = new Restaurante("italia", "braga", "Rua Dr. Carlos Loyd Braga 13", "41,552568, -8,401181", "17,50€", "Il Fiume", "253299171", "ilfiume");
            res.addRestaurante(ilfiume);
            Restaurante porta = new Restaurante("italia", "braga", "Rua dom diogo de sousan, 19", "41,550419, -8,429073", "22,50€", "La Porta Braga", "253268199", "porta");
            res.addRestaurante(porta);
            Restaurante piola = new Restaurante("italia", "braga", "Rua Dom Afonso Henriques, 33", "41,549014, -8,42759", "12,50€", "La Piola", "253096926", "piola");
            res.addRestaurante(piola);
            Restaurante belluci = new Restaurante("italia", "braga", "Avenida Dom Joao II, 43", "41,551292, -8,395405", "22,50€", "Bellucci Pizzaria", "910021763", "belluci");
            res.addRestaurante(belluci);
            Restaurante dartista = new Restaurante("italia", "braga", "Largo Da Soutinha, 15", "41,545872, -8,413471", "10,00€", "Pizza D'artista - Braga", "253618087", "dartista");
            res.addRestaurante(dartista);
            System.out.println("consegui criar restaurantes italianos");

            //japoneses
            Restaurante cosy = new Restaurante("japao", "braga", "Avenida Robert Smith, 38", "41,541781, -8,401831", "26,00€", "Cosy", "253260552", "cosy");
            res.addRestaurante(cosy);
            Restaurante sushiCO = new Restaurante("japao", "braga", "Avenida Alfredo Barros, 72", "41,5365, -8,402935", "15,00€", "Sushi & Co", "915133933", "sushico");
            res.addRestaurante(sushiCO);
            Restaurante lamasan = new Restaurante("japao", "braga", "Rua Nova de Santa Cruz 67", "41,555555, -8,402573", "20,00€", "Lamasan", "253780370", "lamasan");
            res.addRestaurante(lamasan);
            Restaurante casaSushi = new Restaurante("japao", "braga", "Praceta Frei Baltazar de Braga, 15", "41,562053, -8,420443", "17,50€", "Casa do Sushi Braga", "253782044", "casSuxibr");
            res.addRestaurante(casaSushi);
            Restaurante templeSan = new Restaurante("japao", "braga", "Rua de Caires, 280", "41,549788, -8,433248", "12,00€", "Temple San", "253037376", "tmplsan");
            res.addRestaurante(templeSan);

            Restaurante tbrShakai = new Restaurante("japao", "braga", "Praça conde de agrolongo 177", "41,552949, -8,425994", "27,50€", "Taberna Shakai", "253267289", "tbrshk");
            res.addRestaurante(tbrShakai);
            Restaurante seiko = new Restaurante("japao", "braga", "Rua Andrade Corvo, 48", "41,550215, -8,430694", "29,00€", "Seikö japanese restaurant", "935715941", "seiko");
            res.addRestaurante(seiko);
            Restaurante michizaki = new Restaurante("japao", "braga", "Rua Dom Frei Caetano Brandao, 169", "41,548853, -8,428528", "22,50€", "Michizaki", "253086587", "mixizaki");
            res.addRestaurante(michizaki);
            Restaurante omakase = new Restaurante("japao", "braga", "Rua do Raio, 6", "41,55104, -8,418055", "12,50€", "Omakase", "938070831", "omakase");
            res.addRestaurante(omakase);
            System.out.println("consegui criar restaurantes japoneses");

            //espanhois
            Restaurante tapas = new Restaurante("espanha", "braga", "Praca Conde de Agrolongo, 116", "41,552194, -8,426097", "8,00€", "Mercado Das Tapas", "913188076", "tapas");
            res.addRestaurante(tapas);
            Restaurante mostarda = new Restaurante("espanha", "braga", "Praca Conde de Agrolongo, 163", "41,552945, -8,426044", "9,00€", "Mostarda & Chocolate Food Square", "253184646", "mostarda");
            res.addRestaurante(mostarda);
            Restaurante montaditos = new Restaurante("espanha", "braga", "Rua Nova de Santa Cruz, 14", "41,556574, -8,400406", "7,00€", "100 Montaditos Braga", "253773954", "montadit");
            res.addRestaurante(montaditos);
            Restaurante turnCups = new Restaurante("espanha", "braga", "Rua Dom Afonso Henriques São João do Souto", "41,549687, -8,425065", "17,50€", "TurnCups Tapas bar", "969436844", "turncups");
            res.addRestaurante(turnCups);
            Restaurante padeirinhos = new Restaurante("espanha", "braga", "Rua das Granjas", "41,510684, -8,420957", "10,50€", "Os Três Padeirinhos", "253618104", "padeir3");
            res.addRestaurante(padeirinhos);
            Restaurante buteco = new Restaurante("espanha", "braga", "Praca Paulo Vidal, 25", "41,5443, -8,403344", "8,00€", "Buteco das Tapas", "917725657", "buteco");
            res.addRestaurante(buteco);
            System.out.println("consegui criar restaurantes espanhois");

            //turcos
            Restaurante martim = new Restaurante("turquia", "braga", "Avenida de Martim, 807", "41,533897, -8,499211", "6,00€", "Martim Kebab's", "253914101", "martim");
            res.addRestaurante(martim);
            Restaurante kazan = new Restaurante("turquia", "braga", "Rua dos Bombeiros, 19", "41,544626, -8,428631", "5,50€", "Kazan Kebab", "253058131", "kazan");
            res.addRestaurante(kazan);
            Restaurante fastminutes = new Restaurante("turquia", "povoa lanhoso", "Rua Doutor Avelino Pereira de Carvalho, 87", "41,575534, -8,27214", "8,00€", "Fastminutes", "253738458", "fastmns");
            res.addRestaurante(fastminutes);
            Restaurante kingDoner = new Restaurante("turquia", "guimaraes", "Rua Conego Dr Manuel Faria", "41,551117, -8,434988", "6,00€", "King Doner Kebab Pizzas", "933960550", "kngDonr");
            res.addRestaurante(kingDoner);
            Restaurante burcu = new Restaurante("turquia", "famalicao", "Travessa do Outeiro", "41,514656, -8,452215", "7,00€", "Restaurante Burcu Kebab's", "968207297", "burcu");
            res.addRestaurante(burcu);
            Restaurante guima = new Restaurante("turquia", "guimaraes", "Rua Paulo Vi, 152", "41,436293, -8,294763", "6,00€", "Guimaraes Pizzaria Kebab", "920267252", "guipzkb");
            res.addRestaurante(guima);
            Restaurante house = new Restaurante("turquia", "esposende", "Rua dos bombeiros, 19 ", "41,533161, -8,778571", "5,50€", "Kebab House", "964439492", "kbbhs");
            res.addRestaurante(house);
            System.out.println("consegui criar restaurantes trucos");
*/

            Comida com1 = new Comida(1, false, "pizza pepperoni");
            com.addComida(com1);
            Comida com2 = new Comida(2, false, "pizza anchovas");
            com.addComida(com2);
            Comida com3 = new Comida(3, false, "pizza ananas");
            com.addComida(com3);
            Comida com4 = new Comida(4, false, "pizza pepperoni");
            com.addComida(com4);
            Comida com5 = new Comida(5, false, "pizza angolana");
            com.addComida(com5);
            Comida com6 = new Comida(5, false, "Nigiri");
            com.addComida(com6);
            Comida com7 = new Comida(5, false, "Gunkan");
            com.addComida(com7);
            Comida com8 = new Comida(5, false, "Temaki");
            com.addComida(com8);
            Comida com9 = new Comida(5, false, "Hot roll");
            com.addComida(com9);
            Comida com10 = new Comida(5, false, "Joe");
            com.addComida(com10);
            Comida com11 = new Comida(5, false, "Hossomaki Tekkamaki");
            com.addComida(com11);
            Comida com12 = new Comida(5, false, "Uramaki");
            com.addComida(com12);
            Comida com13 = new Comida(5, false, "Hossomaki Kappamaki");
            com.addComida(com13);
            Comida com14 = new Comida(1, true, "salada tomate");
            com.addComida(com14);
            Comida com15 = new Comida(1, true, "salada beterraba");
            com.addComida(com15);
            Comida com16 = new Comida(1, true, "salada cenoura");
            com.addComida(com16);
            Comida com17 = new Comida(1, true, "salada milho");
            com.addComida(com17);
            Comida com18 = new Comida(1, false, "salada frango");
            com.addComida(com18);
            Comida com19 = new Comida(1, false, "salada masrisco");
            com.addComida(com19);
            Comida com20 = new Comida(1, false, "salada atum");
            com.addComida(com20);
            Comida com21 = new Comida(5, false, "kebab");
            com.addComida(com21);
            Comida com22 = new Comida(5, false, "dorum");
            com.addComida(com22);
            Comida com23 = new Comida(5, false, "kebab box");
            com.addComida(com23);
            Comida com24 = new Comida(1, false, "frango frito");
            com.addComida(com24);
            Comida com25 = new Comida(1, false, "frango frito");
            com.addComida(com25);
            Comida com26 = new Comida(1, false, "paelha");
            com.addComida(com26);
            Comida com27 = new Comida(1, false, "tapas");
            com.addComida(com27);
            Comida com28 = new Comida(1, false, "churros");
            com.addComida(com28);
            Comida com29 = new Comida(1, true, "gaspacho");
            com.addComida(com29);
            Comida com30 = new Comida(1, false, "burritos");
            com.addComida(com30);
            Comida com31 = new Comida(1, false, "sandes presunto");
            com.addComida(com31);
            Comida com32 = new Comida(1, true, "noodles");
            com.addComida(com32);

            Cliente cli1 = new Cliente("private53", 2001, 12, 11, "Private", "private53@gmail.com", "953535353", "jotinha53");
            cli.addCliente(cli1);
            Cliente cli2 = new Cliente("miyagi61", 2001, 12, 10, "Miyagi", "miyagi61@gmail.com", "961616161", "mijao61");
            cli.addCliente(cli2);
            Cliente cli3 = new Cliente("extinto77", 2001, 12, 9, "Extinto", "extinto77@gmail.com", "977777777", "tinto77");
            cli.addCliente(cli3);
            Cliente cli4 = new Cliente("nagasaki89", 2001, 12, 8, "Nagasaki", "nagasaki89@gmail.com", "989898989", "naga89");
            cli.addCliente(cli4);

            Avaliacao av1 = new Avaliacao("av1", 4, 2022, 1, 23, "Delicioso!!!", "tapas", "private53");
            ava.addAvaliacao(av1);
            Avaliacao av2 = new Avaliacao("av2", 5, 2021, 9, 11, "serviço rápido e eficaz. Empregadas muito simpáticas, comida muito boa.", "dartista", "nagasaki89");
            ava.addAvaliacao(av2);
            Avaliacao av3 = new Avaliacao("av3", 3, 2022, 1, 6, "boa refeição pós-jejum.", "dartista", "nagasaki89");
            ava.addAvaliacao(av3);
            Avaliacao av4 = new Avaliacao("av4", 3, 2022, 1, 5, "Comida chegou um pouco fria.", "fuhao", "extinto77");
            ava.addAvaliacao(av4);

            System.out.println("fim");



        } catch (SQLException | MaxSizeOvertake | InvalidFormat | AddingError | BDFailedConnection throwables) {
            throwables.printStackTrace();
        }
    }
}