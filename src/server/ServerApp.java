package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.threads.ClientHandler;
import shared.Constants;
import java.util.ArrayList;

public class ServerApp {
    public static void main(String[] args) {
        ArrayList<ClientHandler> clientSocketList = new ArrayList<>();
        try {

            ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);

            while (true) {
                System.out.println("========================");
                System.out.println("Esperando por cliente...");
                Socket clientSocket = serverSocket.accept();
                
                ClientHandler newClient = new ClientHandler(clientSocket);
                
                newClient.start();

                clientSocketList.add(newClient);
            }
        
        } catch (IOException e) {
        
            e.printStackTrace();
        }
    }
}