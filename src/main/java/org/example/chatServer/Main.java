package org.example.chatServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(3030);
        chatServer.start();
    }
}
