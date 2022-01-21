package HTTPAnswer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.Authenticator.Retry;

import DataBase.ClienteDAO;
import DataBase.Tables;
import Exceptions.BDFailedConnection;
import Exceptions.NoMatch;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getByName("localhost"), 8080), 5);

        AuthenticatorTest authenticatorHome = new AuthenticatorTest("/home");
        AuthenticatorTest authenticatorLogin = new AuthenticatorTest("/login/");
        // Login requests
        HttpContext loginContext = server.createContext("/login", exchange -> {
            Headers h = exchange.getResponseHeaders();
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Server.sendFile("Login", h, exchange);
            } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                try{
                    String[] email_pass = Server.divideMessage(bf);
                    if(authenticatorLogin.checkCredentials(email_pass[0], email_pass[1])){
                        redirect("/../home","Home",0,h,exchange,200);
                    }else {
                        redirect("/../index","Index",0,h,exchange,200);
                    }
                } catch (IOException e) {
                    redirect("/../index","Index",0,h,exchange,205);
                }
            }
        });

        // Index requests
        HttpContext indexContext = server.createContext("/", exchange -> {
            Headers h = exchange.getResponseHeaders();
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Server.sendFile("index", h, exchange);
            }
        });

        // Register requests
        HttpContext registerContext = server.createContext("/registo", exchange -> {
            Headers h = exchange.getResponseHeaders();
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Server.sendFile("Registo", h, exchange);
            } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String[] info = divideMessage(bf);
                String nome = info[0], numero = info[1], data_nas = info[2], user = info[3], pass = info[5];
                String email = URLDecoder.decode(info[4], StandardCharsets.UTF_8);

                if (true /* || existsOnBD(email,numero,user) || */) {
                    redirect("/../login","Login",0,h,exchange,200);
                }
                else redirect("/../login","Login",0,h,exchange,200);
            }
        });

        HttpContext homeContext = server.createContext("/home", exchange -> {
            Headers h = exchange.getResponseHeaders();
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {

            } else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

            }
        });

        // homeContext.setAuthenticator(new AuthenticatorATypical("/home", dao));
        homeContext.setAuthenticator(authenticatorHome);
        loginContext.setAuthenticator(authenticatorLogin);

        server.start();
    }

    static public String HtmlText(String filename) throws IOException {
        File file = new File("src/src/HTTPAnswer/" + filename + ".html");
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

    public static void redirect(String path,String pagename,int delay,Headers h, HttpExchange exchange,int rCode) throws IOException {
        StringBuilder builder=new StringBuilder();
        builder.append("<!DOCTYPE HTML><html><head><title> Redirect to ").append(pagename).append(" </title>");
        builder.append("<meta charset=\"UTF-8\"><meta http-equiv=\"refresh\" content=\"").append(delay).append(";url=.html\">");
        builder.append("</head><body>Será redirecionado para ").append(pagename).append("num instante.<br>");
        builder.append("Senão dor redirecionado, clique aqui:<a href=\"").append(path).append("\">click here</a>.</body></html>");
        h.add("Content-Type", "text/html; charset=utf-8");
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
}

class AuthenticatorATypical extends BasicAuthenticator {
    private ClienteDAO dao;
    private String name;

    public AuthenticatorATypical(String realm, ClienteDAO dao) {
        super(realm);
        this.dao = dao;
    }

    @Override
    public com.sun.net.httpserver.Authenticator.Result authenticate(com.sun.net.httpserver.HttpExchange t) {
        Authenticator.Result result;
        if(t.getHttpContext().getPath().equalsIgnoreCase("/login")){
            if(t.getRequestMethod().equalsIgnoreCase("get")) {
                result = new Success(new HttpPrincipal("placeholderUser", "/login"));
            }
            else if(t.getRequestMethod().equalsIgnoreCase("post")){
                BufferedReader bf = new BufferedReader(new InputStreamReader(t.getRequestBody()));
                try{
                    String[] email_pass = Server.divideMessage(bf);
                    if(checkCredentials(email_pass[0],email_pass[1])){
                        result=new Success(new HttpPrincipal(email_pass[0],"/home"));
                    }
                    else result=new Retry(500);
                } catch (IOException e) {
                    result=new Failure(500);

                }
            }
            else result = new Success(new HttpPrincipal("placeholderUser", "/login"));
        }
        else{
            result = super.authenticate(t);
            if (result instanceof Authenticator.Success) {
                HttpPrincipal principal = ((Authenticator.Success) result).getPrincipal();
                name = principal.getUsername();
            }
        }
        return result;
    }

    @Override
    public boolean checkCredentials(String user, String pass) {
        try {
            return dao.getByUsername(user).getPassword().equals(pass);
        } catch (BDFailedConnection | NoMatch e) {
            return false;
        }
    }

    private String getName(){
        return name;
    }
}

class AuthenticatorTest extends BasicAuthenticator {
    private String name;

    public AuthenticatorTest(String realm) {
        super(realm);
    }

    @Override
    public com.sun.net.httpserver.Authenticator.Result authenticate(com.sun.net.httpserver.HttpExchange t) {
        Authenticator.Result result;
        if(t.getHttpContext().getPath().equalsIgnoreCase("/login")){
            if(t.getRequestMethod().equalsIgnoreCase("get")) {
                result = new Success(new HttpPrincipal("placeholderUser", "/login"));
            }
            else if(t.getRequestMethod().equalsIgnoreCase("post")){
                BufferedReader bf = new BufferedReader(new InputStreamReader(t.getRequestBody()));
                try{
                    String[] email_pass = Server.divideMessage(bf);
                    if(checkCredentials(email_pass[0],email_pass[1])){
                        result=new Success(new HttpPrincipal(email_pass[0],"/home"));
                    }
                    else result = new Success(new HttpPrincipal("placeholderUser", "/login"));
                } catch (IOException e) {
                    result=new Failure(500);
                }
            }
            else result = new Success(new HttpPrincipal("placeholderUser", "/login"));
        }
        else{
            result = super.authenticate(t);
            if (result instanceof Authenticator.Success) {
                HttpPrincipal principal = ((Authenticator.Success) result).getPrincipal();
                name = principal.getUsername();
            }
        }
        return result;
    }

    @Override
    public boolean checkCredentials(String user, String pass) {
        return user.equals("teste") && pass.equals("1234");
    }

    public String getName() {
        return name;
    }

}
