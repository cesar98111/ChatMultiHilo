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
        try{
            InetAddress myIp = InetAddress.getLocalHost();
            Socket socket = new Socket(myIp, Constants.SERVER_PORT);
            Scanner scanner = new Scanner(System.in);
            DataOutputStream toServerStream = new DataOutputStream(socket.getOutputStream());
            
            System.out.println("¿Cómo te llamas?");
            String name = scanner.nextLine();
            toServerStream.writeUTF(name);
            ObjectInputStream streamList = new ObjectInputStream(socket.getInputStream());
            DataInputStream fromServerStream = new DataInputStream(socket.getInputStream());
                
            List<String> messageList = (List<String>) streamList.readObject();
            if (messageList.size() > 0){
                for(int i = 0; i<messageList.size() ; i++){
                    System.out.println(messageList.get(i));
                }
            }
            
        
            while (true) {
                
                System.out.println("Introduce el mensaje pal servidor:");
                String message = scanner.nextLine();
                
                if (message.equals("bye")) {
                    toServerStream.writeUTF(message);
                    System.out.println(fromServerStream.readUTF());
                    toServerStream.close();

                    scanner.close();
                    socket.close();
                    break;
                } else if (message.length() <= 6) {
                    System.out.println("Error, La forma para enviar --> (message: <Texto del mensaje>)");
                } else if (message.substring(0,8).equals("message:")){
                    
                    toServerStream.writeUTF(message);
                    new Thread(){
                        public void run(){
                            try{
                                while(true){
                                    System.out.println(fromServerStream.readUTF());
                                }
                            }catch(IOException e){
                                System.out.println("haa");
                                System.out.println(e);
                            }
                        }
                    }.start();
                    
                } else {
                    System.out.println("Error, La forma para enviar --> (message: <Texto del mensaje>)");
                }
            }

           

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("==================");
            System.out.println("ERROR DE CONEXION!");
            System.out.println("LEVANTA EL SERVER!!");
            System.out.println("==================");
        }
    }

}

