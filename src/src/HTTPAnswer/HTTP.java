package src.HTTPAnswer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.net.InetAddress;

public class HTTP implements Runnable{
    public ServerSocket ss;
    public boolean running;
    public Lock l;
    public InetAddress ip;

    HTTP(){
        try {
            ss = new ServerSocket(8080);
        }catch (IOException e){
            e.printStackTrace();
        }
        running = true;
        this.l = new ReentrantLock();
        try{
            this.ip = InetAddress.getLocalHost();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void changeMessage(String message){
        l.lock();
        try {
        }finally {
            l.unlock();
        }
    }

    public void addMessage(String message){
        l.lock();
        try {
    //        String aux = this.feedback;
      //      this.feedback = aux + "<br>" + "<b>" + message + "<b>";
        }finally {
            l.unlock();
        }
    }

    public void turnOFF() throws IOException {
        running = false;
        Socket s = new Socket(ip,8080);
        s.close();
    }

    private void sendRegisto(OutputStream clientOutput)throws IOException{
        File file = new File("src/src/HTTPAnswer/Registo.html");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String msg;
        clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
        clientOutput.write("\r\n".getBytes());
        while((msg = br.readLine()) != null)clientOutput.write(msg.getBytes(StandardCharsets.UTF_8));
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
    }

    private void sendLogin(OutputStream clientOutput)throws IOException{
        File file = new File("src/src/HTTPAnswer/Login.html");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String msg;
        clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
        clientOutput.write("\r\n".getBytes());
        while((msg = br.readLine()) != null)clientOutput.write(msg.getBytes(StandardCharsets.UTF_8));
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
    }

    private void sendIndex(OutputStream clientOutput)throws IOException{
        File file = new File("src/src/HTTPAnswer/index.html");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String msg;
        clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
        clientOutput.write("\r\n".getBytes());
        while((msg = br.readLine()) != null)clientOutput.write(msg.getBytes(StandardCharsets.UTF_8));
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = ss.accept();
            OutputStream clientOutput = clientSocket.getOutputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while(running){
                String request = input.readLine();
                System.out.println(request);
                String[] requestParam = request.split(" ");
                switch(requestParam[0]){
                    case "GET":
                        switch(requestParam[1]){
                            case "/":
                                sendIndex(clientOutput);
                                break;
                            case "/registo":
                                sendRegisto(clientOutput);
                                break;  
                            case "/login":  
                            sendLogin(clientOutput);
                                break;   
                        }
                        break;
                    default:
                        running=false;    
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        HTTP http = new HTTP();
        new Thread(http).start();
    }
}