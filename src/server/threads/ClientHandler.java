package server.threads;

import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
}