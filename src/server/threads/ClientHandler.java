package server.threads;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientHandler extends Thread {

    private Socket clientSocket;
    private MessageList listMessage;


    public ClientHandler(Socket socket, MessageList listMessage) {
        this.clientSocket = socket;
        this.listMessage = listMessage;
    }

    @Override
    public void run() {
        try {
            System.out.println("========================");
            String msg = "La conexión se ha realizado con ";
            msg += this.clientSocket.getInetAddress().toString();
            System.out.println(msg);

            System.out.println("========================");
            DataInputStream fromClientStream = new DataInputStream(this.clientSocket.getInputStream());
            String clientName = fromClientStream.readUTF();

            while (true) {
                String clientMessage = fromClientStream.readUTF();
                String message = clientName + "dice: " + clientMessage;
                
                System.out.println(message);
                listMessage.saveMessage(message);
                if (clientMessage.equals("bye")) {
                    break;
                }
            }
            fromClientStream.close();
            System.out.println("-->" + clientName + " se ha ido.");
        } catch (IOException e) {
            System.out.println("Oh no, esesión!");
        }
    }
}