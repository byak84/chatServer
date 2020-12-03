package org.example.chatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private ChatServer chatServer = null;
    private Socket clientSocket = null;
    private PrintWriter outMessage = null;
    private Scanner inMessage = null;

    public ClientHandler(ChatServer chatServer, Socket clientSocket) {
        try {
            this.chatServer = chatServer;
            this.clientSocket = clientSocket;
            this.outMessage = new PrintWriter(clientSocket.getOutputStream());
            this.inMessage = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() throws NoSuchElementException {
        this.chatServer.sendMessageToAllClients("Новый участник в чате", null);
        boolean running = true;
        while (running) {
            String clientMessage = this.inMessage.nextLine();
            this.chatServer.sendMessageToAllClients(clientMessage, this);
            if (clientMessage.equalsIgnoreCase("exit")) running = false;
        }
        try {
            this.clientSocket.close();
            this.chatServer.logging("Client closed the connection!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("2");
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
