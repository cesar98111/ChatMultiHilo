package server.threads;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread {


    private Socket clientSocket;
    private MessageList listMessage;

    public ClientHandler(Socket socket, MessageList listMessage) {
        this.clientSocket = socket;
        this.listMessage = listMessage;
    }

    public String dateFormat(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
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
                String message = clientName + clientMessage;
                System.out.println(message);
                String withoutWordMessage = clientMessage.substring(9);
                message = "[" + dateFormat() + "] " + "<" + clientName + "> : <" + withoutWordMessage + ">";
                listMessage.saveMessage(message);
                if (clientMessage.equals("bye")) {
                    break;
                }
            }
            fromClientStream.close();
            System.out.println("-->" + clientName + " se ha ido.");
        } catch (IOException e) {
            System.out.println("Oh no, sesión!");
        }
    }
}