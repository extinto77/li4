package HTTPAnswer;

import DataBase.ClienteDAO;
import Exceptions.BDFailedConnection;
import Exceptions.NoMatch;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Autenticador {
    private ClienteDAO clienteDAO;
    private String realm;
    private ReentrantLock lock=new ReentrantLock();

    public Autenticador(String realm){
        this.realm=realm;
    }

    public Autenticador(ClienteDAO clienteDAO,String realm){
        this.clienteDAO=clienteDAO;
        this.realm=realm;
    }

    public void autenticar(HttpExchange exchange){
        lock.lock();
        String[] credentials=extractCredentials(exchange);
        Headers h=exchange.getResponseHeaders();
        h.add("WWW-Authenticate","Basic realm=\""+realm+"\", charset=\"UTF-8\"");
        try{
            assert credentials != null;
            if(clienteDAO.getByField(credentials[0],"email").getPassword().equals(credentials[1])){
                exchange.sendResponseHeaders(200,0);
                exchange.close();
            }
            else{
                exchange.sendResponseHeaders(400,0);
                exchange.close();
            }
        } catch (Exception e) {
            exchange.close();
        } finally {
            lock.unlock();
        }
    }

    public static String[] extractCredentials(HttpExchange exchange){
        BufferedReader reader=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        try{
            StringBuilder msg = new StringBuilder();
            while (reader.ready()) {
                msg.append(reader.readLine());
            }
            String[]aux1=msg.toString().split("name=\"email\"");
            String[]aux2=aux1[1].split("-");
            String[]aux3=aux1[1].split("name=\"password\"");
            String[]aux4=aux3[1].split("-");
            String[]res=new String[2];
            res[0]=aux2[0];
            res[1]=aux4[0];
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    public static String[] getCredentials(HttpExchange exchange){
        List<String> header=exchange.getRequestHeaders().get("Authentication");
        String authentication;
        if(header==null)return null;
        else authentication=header.get(0);
        if(authentication!=null && authentication.toLowerCase().startsWith("basic")){
            String base64Credentials = authentication.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            return credentials.split(":", 2);
        }
        else return null;
    }
}


/*
    if(credentials!=null&&credentials[0].equals("teste")&&credentials[1].equals("1234")) {
                Server.redirect("/home","Home",0,h,exchange,200);
            }
            else{
                Server.redirect("/","Index",0,h,exchange,200);
            }

 */
