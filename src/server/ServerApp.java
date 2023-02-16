package server;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectOutputStream;
import server.threads.ClientHandler;
import server.threads.MessageList;
import shared.Constants;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    public static void main(String[] args) {

        MessageList messageList = new MessageList();
        ArrayList<ClientHandler> clientSocketList = new ArrayList<>();
        try {

            ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);

            while (true) {
                System.out.println("========================");
                System.out.println("Esperando por cliente...");
                Socket clientSocket = serverSocket.accept();
                ObjectOutputStream toList = new ObjectOutputStream(clientSocket.getOutputStream());
                toList.writeObject(messageList.getMessage());

                ClientHandler newClient = new ClientHandler(clientSocket, messageList);
                
                newClient.start();

                clientSocketList.add(newClient);
            }
        
        } catch (IOException e) {
        
            e.printStackTrace();
        }
    }
}