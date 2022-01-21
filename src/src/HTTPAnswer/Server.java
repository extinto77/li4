package src.HTTPAnswer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import src.DataBase.Tables;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server=HttpServer.create(new InetSocketAddress(InetAddress.getByName("localhost"),8080),5);

        //Login requests
        server.createContext("/login", exchange -> {
            Headers h=exchange.getResponseHeaders();
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                Server.sendFile("Login",h,exchange);
            }
            else if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String[] email_pass = divideMessage(bf);
                if(email_pass[0].equals("ola") && email_pass[1].equals("1234")){ // MUDAR A CONDICAO PARA VERIFICAR A BD
                    Server.sendFile("index",h,exchange);
                }else{
                    Server.sendFile("Login",h,exchange);
                }
            }
        });

        //Index requests
        server.createContext("/", exchange -> {
            Headers h=exchange.getResponseHeaders();
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                Server.sendFile("index",h,exchange);
            }
        });

        //Register requests
        server.createContext("/registo", exchange -> {
            Headers h=exchange.getResponseHeaders();
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                Server.sendFile("Registo",h,exchange);
            }
            else if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String[] info = divideMessage(bf);
                String nome = info[0], numero = info[1], data_nas = info[2], user=info[3], pass=info[5];
                String email = URLDecoder.decode(info[4],StandardCharsets.UTF_8);

                if(true /*|| existsOnBD(email,numero,user) || */){
                    System.out.println(Arrays.toString(info));
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

    public static void sendFile(String filename, Headers h, HttpExchange exchange) throws IOException {
        String body=Server.HtmlText(filename);
        h.add("Content-Type","text/html; charset=utf-8");
        byte[]bytes=body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200,bytes.length);
        OutputStream os=exchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }

    public static String[] divideMessage(BufferedReader bf) throws IOException {
        StringBuilder msg = new StringBuilder();
        while (bf.ready()){
            msg.append(bf.readLine());
        }
        String[] items = msg.toString().split("&");
        String[] res = new String[items.length];
        for(int i = 0; i < items.length ; i++){
            res[i] = items[i].split("=")[1];
        }
        return res;
    }
}


