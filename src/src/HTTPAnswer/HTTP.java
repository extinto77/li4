package src.HTTPAnswer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//AGORA É A CLASSE SERVER QUE CORRE O SERVER HTTTP
public class HTTP implements Runnable {
    private InputStream input;
    private Socket socket;
    private PrintWriter output;

    //AGORA É A CLASSE SERVER QUE CORRE O SERVER HTTTP
    public HTTP(Socket s) throws IOException {
        input = s.getInputStream();
        output = new PrintWriter(s.getOutputStream());
        socket=s;
    }

    //AGORA É A CLASSE SERVER QUE CORRE O SERVER HTTTP
    private void sendHtml(String filename) throws IOException {
        File file = new File("src/src/HTTPAnswer/"+filename+".html");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String msg;
        output.println("HTTP/1.1 200 OK");
        output.println("");
        while ((msg = br.readLine()) != null)
            output.print(msg);
        output.println("");
        output.flush();
    }

    //AGORA É A CLASSE SERVER QUE CORRE O SERVER HTTTP
    private void readLogin() throws IOException {
    }
    //AGORA É A CLASSE SERVER QUE CORRE O SERVER HTTTP
    private void readRegisto() {
    }

    //AGORA É A CLASSE SERVER QUE CORRE O SERVER HTTTP
    @Override
    public void run() {
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(input));
            String request = reader.readLine();
            String[] requestParam = request.split(" ", 3);
            switch (requestParam[0]) {
                case "GET":
                    switch (requestParam[1]) {
                        case "/":
                            sendHtml("index");
                            break;
                        case "/registo":
                            sendHtml("Registo");
                            break;
                        case "/login":
                            sendHtml("Login");
                            break;
                    }
                    break;
                case "POST":
                    switch (requestParam[1]) {
                        case "/registo":
                            readRegisto();
                            break;
                        case "/login":
                            readLogin();
                            break;
                    }
                default:

                    break;

            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //AGORA É A CLASSE SERVER QUE CORRE O SERVER HTTTP
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        while(true){
            Socket s = ss.accept();
            HTTP http = new HTTP(s);
            new Thread(http).start();
        }
    }
}