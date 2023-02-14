package server;

//import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import shared.Constants;

import java.util.ArrayList;

public class ServerApp {
    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);

            while (true) {
                System.out.println("========================");
                System.out.println("Esperando por cliente...");

            }

            // System.out.println("========================");
            // System.out.println("Cerrando el servidor");
            // serverSocket.close();
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}