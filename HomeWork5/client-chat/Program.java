package ru.geekbrains.chat.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите имя: ");
            String name = scanner.nextLine();
            // ____________Информация о подключении______________
            Socket socket = new Socket("localhost", 1400);
            InetAddress inetAddress = socket.getInetAddress();

            Client client = new Client(socket, name);
            System.out.println("InetAddress: " + inetAddress);
            String remotIp = inetAddress.getHostAddress();
            System.out.println("remot IP: " + remotIp);
            System.out.println("local port: " + socket.getLocalPort());

            client.listenMessage();
            client.sendMessage();


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
