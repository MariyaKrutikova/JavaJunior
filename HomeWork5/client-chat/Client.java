package ru.geekbrains.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private String name;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket, String userName) {
        this.socket = socket;
        this.name = userName;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEveryThing(socket, bufferedWriter, bufferedReader);
        }
    }

    private void closeEveryThing(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (socket.isConnected()) {
                    try {
                        message = bufferedReader.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        closeEveryThing(socket, bufferedWriter, bufferedReader);
                    }

                }
            }
        }).start();
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                String[] wordsInMessage = message.split(":");
                String messageWithoutName = wordsInMessage[1];
                if (wordsInMessage[0].charAt(0) == '@') {
                    String nameForMessage = wordsInMessage[0].substring(1);
                    bufferedWriter.write(name + ": " + messageWithoutName);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            closeEveryThing(socket, bufferedWriter, bufferedReader);
        }
    }


//    public void sendMessage() {
//        try {
//            bufferedWriter.write(name);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//            Scanner scanner = new Scanner(System.in);
//            while (socket.isConnected()) {
//                String message = scanner.nextLine();
//                bufferedWriter.write(name + ": " + message);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//                }
//            } catch(IOException e){
//            closeEveryThing(socket, bufferedWriter, bufferedReader);
//        }
//    }
}

