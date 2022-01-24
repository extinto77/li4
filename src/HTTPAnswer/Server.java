package HTTPAnswer;

import DataBase.*;
import Exceptions.AddingError;
import Exceptions.BDFailedConnection;
import Exceptions.InvalidFormat;
import Exceptions.MaxSizeOvertake;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException {

        try {
            // imprimeIP();
            Tables bd = JDBC.iniciaBD();

            HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 8080), 5);

            Autenticador autenticador = new Autenticador(bd.getCli(), "/home");

            //Login requests
            HttpContext loginContext = server.createContext("/home/login", exchange -> {
                System.out.println("1");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                    Server.sendFile("Login", h, exchange);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                    autenticador.autenticar(exchange);
                }
            });

            //Index requests
            HttpContext indexContext = server.createContext("/", exchange -> {
                System.out.println("index");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                    if (exchange.getRequestURI().toString().equals("/bingMaps.js")) {
                        Server.sendFileJS("bingMaps", h, exchange);
                    } else {
                        System.out.println(exchange.getRequestURI().toString());
                        Server.sendFile("index", h, exchange);
                    }
                }
            });

            // Register requests
            HttpContext registerContext = server.createContext("/home/registo", exchange -> {
                System.out.println("3");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                    Server.sendFile("Registo", h, exchange);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                    BufferedReader bf = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    String[] info = divideMessage(bf);
                    String nome = info[0], numero = info[1], data_nas = info[2], user = info[3], pass = info[5];
                    String email = URLDecoder.decode(info[4], StandardCharsets.UTF_8);

                    try {
                        int res = bd.getCli().existsOnBD(email,numero,user);
                        if (res == 0) {
                            String[] data = data_nas.split("-");
                            bd.getCli().addCliente(new Cliente(user,Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]),nome,email,numero,pass));
                            exchange.sendResponseHeaders(200,0);
                            exchange.close();
                        }
                        else{
                            int error = 200 + res;
                            exchange.sendResponseHeaders(error,0);
                            exchange.close();
                        }
                    } catch (BDFailedConnection | InvalidFormat | MaxSizeOvertake | AddingError e) {
                        e.printStackTrace();
                    }
                }
            });

            // Home requests
            HttpContext homeContext = server.createContext("/home", exchange -> {
                System.out.println("4");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                    try {
                        String aux = bd.getRes().getAllCoordenates();
                        //StringBuilder file =
                       // home.
                    } catch (BDFailedConnection e) {
                        e.printStackTrace();
                    }
                    Server.sendFile("home", h, exchange);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                    Server.sendFile("home", h, exchange);
                }
            });

            HttpContext homeSettingsContext = server.createContext("/home/settings", exchange -> {
                System.out.println("5");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                    Server.sendFile("settings", h, exchange);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                    BufferedReader bf = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    String[] info = divideMessage(bf);
                    if (info[0].equals("elimina")) {
                        // ELIMINAR CONTA
                        redirect("/../index", "Index", 0, h, exchange, 200);
                    } else {
                        System.out.println(Arrays.toString(info));
                    }
                }
            });

            HttpContext AvaliacaoContext = server.createContext("/home/avaliacao", exchange -> {
                System.out.println("6");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                    Server.sendFile("avaliacao", h, exchange);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                }
            });

            HttpContext InfoContext = server.createContext("/home/info", exchange -> {
                System.out.println("7");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                    String body=Server.HtmlText("informacao");

                    String path=exchange.getRequestURI().getQuery();
                    String id="";
                    if(path!=null&&path.length()>0){
                        String[]query=path.split("=");
                        if(query[0].equals("id")&&query.length>1){
                            id=query[1];
                        }
                    }

                    try {
                        Restaurante res;
                        if(id.length()>0){
                            res= bd.getRes().getByIdRestaurante(id);
                            if(res!=null){
                                body = body.replace("<h1>TITULO</h1>","<h1>"+res.getNome()+"</h1>");
                                body = body.replace("€ - €€",String. format("%.2f", res.getPreco())+"€");
                                body = body.replace("Lorem ipsum dolor sit amet", res.getDescricao()+"<br><br><br>");
                                List<Avaliacao> avalicoes=bd.getAva().getAvaliacoes("idRestaurante",id);
                                StringBuilder avals= new StringBuilder();
                                for(Avaliacao av:avalicoes){
                                    avals.append(av.getHTML());
                                }
                                body = body.replace("Aenean commodo ligula eget dolor",avals.toString());
                            }
                        }
                        h.add("Content-Type", "text/html; charset=utf-8");
                        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
                        exchange.sendResponseHeaders(200, bytes.length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(bytes);
                        os.flush();
                        os.close();
                    } catch (BDFailedConnection | InvalidFormat bdFailedConnection) {
                        bdFailedConnection.printStackTrace();
                    }




                    Server.sendFile("informacao", h, exchange);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

                }
            });

            CookieHandler.setDefault(new CookieManager());
            server.start();
        } catch (SQLException | SocketException e) {
            e.printStackTrace();
        }
    }

    private static void sendFileJS(String bingMaps, Headers h, HttpExchange exchange) throws IOException {
        String body = Server.JsText(bingMaps);
        h.add("Content-Type", "text/javascript; charset=utf-8");
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }

    static public String HtmlText(String filename) throws IOException {
        File file = new File("src/HTTPAnswer/" + filename + ".html");
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String msg;
        while ((msg = br.readLine()) != null)
            sb.append(msg);
        return sb.toString();
    }

    static public String JsText(String filename) throws IOException {
        File file = new File("src/HTTPAnswer/" + filename + ".js");
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String msg;
        while ((msg = br.readLine()) != null)
            sb.append(msg);
        return sb.toString();
    }

    public static void sendFile(String filename, Headers h, HttpExchange exchange) throws IOException {
        String body = Server.HtmlText(filename);
        h.add("Content-Type", "text/html; charset=utf-8");
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }

    public static void redirect(String path, String pagename, int delay, Headers h, HttpExchange exchange, int rCode)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE HTML><html><head><title> Redirect to ").append(pagename).append(" </title>");
        builder.append("<meta charset=\"UTF-8\"><meta http-equiv=\"refresh\" content=\"").append(delay).append(";url=")
                .append(path).append("\">");
        builder.append("</head><body>Será redirecionado para ").append(pagename).append("num instante.<br>");
        builder.append("Senão dor redirecionado, clique aqui:<a href=\"").append(path)
                .append("\">click here</a>.</body></html>");
        h.add("Content-Type", "text/html; charset=utf-8");
        List<String> cookies = exchange.getResponseHeaders().get("Set-Cookie");
        if (cookies != null) {
            StringBuilder cookieString = new StringBuilder();
            boolean first = true;
            for (String cookie : cookies) {
                if (!first) {
                    cookieString.append("; ");
                } else
                    first = false;
                cookieString.append(cookie);
            }
            h.add("Set-Cookie", cookieString.toString());
        }
        byte[] bytes = builder.toString().getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(rCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }

    public static String[] divideMessage(BufferedReader bf) throws IOException {
        StringBuilder msg = new StringBuilder();
        while (bf.ready()) {
            msg.append(bf.readLine());
        }
        String[] items;
        String[] res;
        if(msg.toString().startsWith("------WebKitForm")){
            res = parseForm(msg.toString());
        }else{
            items = msg.toString().split("&");
            res = new String[items.length];
            for (int i = 0; i < items.length; i++) {
                res[i] = items[i].split("=")[1];
            }
        }


        return res;
    }

    public static String toHTML(String str) {
        StringBuilder out = new StringBuilder();
        for (char c: str.toCharArray()) {
            if(!Character.isLetterOrDigit(c))
                out.append(String.format("&#x%x;", (int) c));
            else
                out.append(String.format("%s", c));

        }
        return out.toString();
    }

    private static String[] parseForm(String msg){
        String[]aux1=msg.split("name=\"nome\"");
        String[]aux2=aux1[1].split("-");
        String[]aux3=aux1[1].split("name=\"numero\"");
        String[]aux4=aux3[1].split("-");
        String[]aux5=aux3[1].split("name=\"nascimento\"");
        String[]aux6=aux5[1].split("-----");
        String[]aux7=aux5[1].split("name=\"username\"");
        String[]aux8=aux7[1].split("-");
        String[]aux9=aux7[1].split("name=\"email\"");
        String[]aux10=aux9[1].split("-");
        String[]aux11=aux9[1].split("name=\"password\"");
        String[]aux12=aux11[1].split("-");
        String[]res=new String[6];
        res[0]=aux2[0];
        res[1]=aux4[0];
        res[2]=aux6[0];
        res[3]=aux8[0];
        res[4]=aux10[0];
        res[5]=aux12[0];
        return res;
    }

    /*
     * private static void imprimeIP() throws SocketException, UnknownHostException
     * {
     * Enumeration en = NetworkInterface.getNetworkInterfaces();
     * String ip = "";
     * while(en.hasMoreElements())
     * {
     * NetworkInterface n = (NetworkInterface) en.nextElement();
     * Enumeration ee = n.getInetAddresses();
     * while (ee.hasMoreElements())
     * {
     * InetAddress i = (InetAddress) ee.nextElement();
     * if(i instanceof Inet4Address &&
     * !i.equals(Inet4Address.getByName("127.0.0.1"))){
     * ip = i.getHostAddress();
     * System.out.println("The Server's IP is:\n\t"+ip);
     * }
     * }
     * }
     * }
     */
}