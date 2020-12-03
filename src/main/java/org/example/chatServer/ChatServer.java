package org.example.chatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private int serverPort;
    private List<ClientHandler> clients = new ArrayList<ClientHandler>();

    public ChatServer(int port) {
        this.serverPort = port;
    }

    public void start() throws IOException {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.serverPort);
            System.out.println("Сервер запущен!");
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Подключился новый клиент.");
                ClientHandler client = new ClientHandler(this, clientSocket);
                clients.add(client);
                new Thread(client).start();
            }

        } catch (IOException e) {
            System.out.println("3");
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
