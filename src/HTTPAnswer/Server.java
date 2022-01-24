package HTTPAnswer;

import DataBase.JDBC;
import DataBase.Tables;
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

            // Login requests
            HttpContext loginContext = server.createContext("/home/login", exchange -> {
                System.out.println("1");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                    Server.sendFile("Login", h, exchange);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                    autenticador.autenticar(exchange);
                }
            });

            // Index requests
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

                    if (true /* || existsOnBD(email,numero,user) || */) {
                        redirect("/../home/login", "Login", 0, h, exchange, 200);
                    } else
                        redirect("/../index", "Index", 0, h, exchange, 200);
                }
            });

            // Home requests
            HttpContext homeContext = server.createContext("/home", exchange -> {
                System.out.println("4");
                Headers h = exchange.getResponseHeaders();
                if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
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
        String[] items = msg.toString().split("&");
        String[] res = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            res[i] = items[i].split("=")[1];
        }
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