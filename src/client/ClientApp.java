package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.List;

import java.io.ObjectInputStream;
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
            ObjectInputStream streamList = new ObjectInputStream(socket.getInputStream());
            
            List<String> messageList = (List<String>) streamList.readObject();
            if (messageList.size() > 0){
                for(int i = 0; i<messageList.size() ; i++){
                    System.out.println(messageList.get(i));
                }
            }
            
        
            while (true) {
                System.out.println("Introduce el mensaje pal servidor:");
                String message = scanner.nextLine();
                String clientWordMessage = message.substring(0,7);
                if (clientWordMessage.equals("message")) {
                    toServerStream.writeUTF(message);
                } else {
                    System.out.println("Mensaje error");
                }
                
                if (message.equals("bye")) {
                    break;
                }
            }

            toServerStream.close();

            
            scanner.close();
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("==================");
            System.out.println("ERROR DE CONEXION!");
            System.out.println("LEVANTA EL SERVER!!");
            System.out.println("==================");
        }
    }
}

