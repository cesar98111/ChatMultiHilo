package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import shared.Constants;

public class ClientApp {
    public static void main(String[] args) {

        try {

            InetAddress myIp = InetAddress.getLocalHost();
            Socket socket = new Socket(myIp, Constants.SERVER_PORT);

            Scanner scanner = new Scanner(System.in);
            DataOutputStream toServerStream = new DataOutputStream(socket.getOutputStream());
            
            System.out.println("¿Cómo te llamas?");
            String name = scanner.nextLine();
            toServerStream.writeUTF(name);

            while (true) {
                System.out.println("Introduce el mensaje pal servidor:");
                String message = scanner.nextLine();
                toServerStream.writeUTF(message);
                
                if (message.equals("bye")) {
                    break;
                }
            }

            toServerStream.close();

            
            scanner.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("==================");
            System.out.println("ERROR DE CONEXION!");
            System.out.println("LEVANTA EL SERVER!!");
            System.out.println("==================");
        }
    }
}

