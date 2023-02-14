package server.threads;

import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
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
                System.out.println(clientName + " dice: " + clientMessage);
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