package ru.geekbrains.chat.server;

import javax.management.relation.RoleUnresolved;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class CLientManager implements Runnable {
    private final Socket socket;
    private  final static ArrayList<CLientManager> clients = new ArrayList<>();
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;

    public CLientManager(Socket socket) {
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            name = bufferedReader.readLine();
            clients.add(this);
            System.out.println(name + " подключился");
        } catch (IOException e) {
            closeEveryThing(socket, bufferedWriter, bufferedReader);
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()){
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEveryThing(socket, bufferedWriter, bufferedReader);
                break;
            }

        }
    }

//    private void broadcastMessage(String message){
//        String[] wordsInMessage = message.split(":");
//        String messageWithoutName = wordsInMessage[1];
//        if (wordsInMessage[0].charAt(0) == '@') {
//            String nameForMessage = wordsInMessage[0].substring(1);
//            System.out.println(nameForMessage);
//            for (CLientManager client: clients) {
//                try {
//                    if (client.name.equals(nameForMessage)) {
//                        client.bufferedWriter.write(messageWithoutName);
//                        client.bufferedWriter.newLine();
//                        client.bufferedWriter.flush();
//                    }
//                    else {
//                        if (!client.name.equals(name)) {
//                            try {
//                                client.bufferedWriter.write(message);
//                                client.bufferedWriter.newLine();
//                                client.bufferedWriter.flush();
//                            } catch (IOException e) {
//                                closeEveryThing(socket, bufferedWriter, bufferedReader);
//                            }
//                        }
//                    }
//                } catch (IOException e) {
//                    closeEveryThing(socket, bufferedWriter, bufferedReader);
//                }
//            }
//        }
//
//    }

    private void broadcastMessage(String message){
        for (CLientManager client: clients) {
            try {
                    if (!client.name.equals(name)) {
                        client.bufferedWriter.write(message);
                        client.bufferedWriter.newLine();
                        client.bufferedWriter.flush();
                    }
            } catch (IOException e) {
                closeEveryThing(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    private void closeEveryThing(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        removeClient();
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void removeClient(){
        clients.remove(this);
        System.out.println(name + " покинул чат");
    }
}
