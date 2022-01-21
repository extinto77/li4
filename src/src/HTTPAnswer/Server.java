package src.HTTPAnswer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import src.DataBase.Tables;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server=HttpServer.create(new InetSocketAddress(InetAddress.getByName("localhost"),8080),5);

        //Login requests
        server.createContext("/login", exchange -> {
            Headers h=exchange.getResponseHeaders();
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                String body=Server.HtmlText("Login");
                h.add("Content-Type","text/html; charset=utf-8");
                byte[]bytes=body.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200,bytes.length);
                OutputStream os=exchange.getResponseBody();
                os.write(bytes);
                os.flush();
                os.close();
            }
            else if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                while (bf.ready()){
                    System.out.println(bf.readLine());
                }
            }
        });
        //Index requests
        server.createContext("/", exchange -> {
            Headers h=exchange.getResponseHeaders();
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                String body=Server.HtmlText("index");
                h.add("Content-Type","text/html; charset=utf-8");
                byte[]bytes=body.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200,bytes.length);
                OutputStream os=exchange.getResponseBody();
                os.write(bytes);
                os.flush();
                os.close();
            }
            else if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                while (bf.ready()){
                    System.out.println(bf.readLine());
                }
            }
        });

        //Register requests
        server.createContext("/Registo", exchange -> {
            Headers h=exchange.getResponseHeaders();
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                String body=Server.HtmlText("Registo");
                h.add("Content-Type","text/html; charset=utf-8");
                byte[]bytes=body.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200,bytes.length);
                OutputStream os=exchange.getResponseBody();
                os.write(bytes);
                os.flush();
                os.close();
            }
            else if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                while (bf.ready()){
                    System.out.println(bf.readLine());
                }
            }
        });

        server.start();
    }

    static public String HtmlText(String filename) throws IOException {
        File file = new File("src/src/HTTPAnswer/"+filename+".html");
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb=new StringBuilder();
        String msg;
        while ((msg = br.readLine()) != null) sb.append(msg);
        return sb.toString();
    }

}


