package server.threads;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ClientHandler extends Thread {


    private Socket clientSocket;
    private MessageList listMessage;
    private ArrayList<ClientHandler> listaCliente;
    public ClientHandler(Socket socket, MessageList listMessage, ArrayList<ClientHandler> listaCliente) {
        this.clientSocket = socket;
        this.listMessage = listMessage;
        this.listaCliente = listaCliente;
    }

    public String dateFormat(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public Socket getSoket(){
        return this.clientSocket;
    }
    @Override
    public void run() {
        try {
            System.out.println("========================");
            String msg = "La conexión se ha realizado con ";
            msg += this.clientSocket.getInetAddress().toString();
            System.out.println(msg);

            System.out.println("========================");
            DataOutputStream toClientStream = new DataOutputStream(this.clientSocket.getOutputStream());
            DataInputStream fromClientStream = new DataInputStream(this.clientSocket.getInputStream());
            String clientName = fromClientStream.readUTF();
            System.out.println(clientName);
            while (true) {
                
                    String clientMessage = fromClientStream.readUTF();
                   
                    String message = clientName + clientMessage;
                    System.out.println(message);
                
                
                if (clientMessage.equals("bye")) {
                    toClientStream.writeUTF("GOODBYE");
                    
                    break;
                }else{
                    System.out.println("hola");
                    String withoutWordMessage = clientMessage.substring(9);
                    message = "[" + dateFormat() + "] " + "<" + clientName + "> : <" + withoutWordMessage + ">";
                    listMessage.saveMessage(message);
                    for(int i = 0; i<listaCliente.size() ; i++){
                        System.out.println("hola");
                        Socket soket = listaCliente.get(i).getSoket();
                        new DataOutputStream(soket.getOutputStream()).writeUTF(message);
                        
                     }
                }
                }
            
            fromClientStream.close();
            System.out.println("-->" + clientName + " se ha ido.");
        } catch (IOException e) {
            System.out.println("Oh no, sesión!");
        }
    }
}