package ru.geekbrains.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public  void runServer(){
       try {
           while (!serverSocket.isClosed()) {
               Socket socket = serverSocket.accept();
               CLientManager cLientManager = new CLientManager(socket);
               System.out.println("Новое подключение");
               Thread thread = new Thread(cLientManager);
               thread.start();
           }
       } catch (IOException e){
           closeSocket();
       }
    }

    private void closeSocket(){
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
