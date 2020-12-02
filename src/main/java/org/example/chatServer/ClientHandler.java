package org.example.chatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private ChatServer chatServer = null;
    private Socket clientSocket = null;
    private PrintWriter outMessage = null;

    public ClientHandler(ChatServer chatServer, Socket clientSocket) {
        try {
            this.chatServer = chatServer;
            this.clientSocket = clientSocket;
            this.outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.chatServer.sendMessageToAllClients("Новый участник в чате");
        Boolean running = true;
        while (running) {
            String clientMessage = null;
            try {
                clientMessage = new Scanner(clientSocket.getInputStream()).nextLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.chatServer.logging(clientMessage);
            if (clientMessage.equalsIgnoreCase("exit")) running = false;
        }

    }

    public void sendMsg(String msg) {
        try {
            this.outMessage.println(msg);
            this.outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
