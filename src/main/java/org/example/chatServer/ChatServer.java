package org.example.chatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private final int serverPort;
    private final List<ClientHandler> clients = new ArrayList<>();

    public ChatServer(int port) {
        this.serverPort = port;
    }

    public void start() {
        Socket clientSocket;
        ServerSocket serverSocket;
        boolean running = true;
        try {
            serverSocket = new ServerSocket(this.serverPort);
            System.out.println("Server is started!");
            while (running) {
                clientSocket = serverSocket.accept();
                System.out.println("New client connected. " + clients.size());
                ClientHandler client = new ClientHandler(this, clientSocket);
                clients.add(client);
                new Thread(client).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToAllClients(String msg, ClientHandler clientHandler) {
        for (ClientHandler o : this.clients) {
            if (clientHandler != o) o.sendMsg(msg);
        }
    }

    public void logging(String logString) {
        System.out.println(logString);
    }
}
