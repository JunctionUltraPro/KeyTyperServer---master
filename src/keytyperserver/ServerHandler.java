package keytyperserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHandler {

    private final int PORT = 1995;
    private final ArrayList<ClientInfo> connectedClient = new ArrayList();

    private static ServerHandler instance = new ServerHandler();
    public static Window window;

    private ServerHandler() {

    }

    public static ServerHandler getInstance() {
        return ServerHandler.instance;
    }

    public void start() {
        try {
            window = new Window("Quicro");
            ServerSocket server = new ServerSocket(PORT);
            while (true) {
                Socket newSocket = server.accept();
                System.out.println("New client connected to socket NO." + newSocket.getRemoteSocketAddress().toString().substring(17));
                window.changeStatus("connected");
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        window.changeStatus("connected");
                    }

                }).start();
                window.changeStatus("...");
                window.addClient(newSocket.getRemoteSocketAddress().toString().substring(17), newSocket.getInetAddress().toString().split("/")[1]);
                ClientInfo newClient = new ClientInfo(newSocket);
                connectedClient.add(newClient);
                new Thread(newClient).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientInfo getClient(Socket socket) {
        for (ClientInfo i : connectedClient) {
            if (i.getSocket() == socket) {
                return i;
            }
        }
        return null;
    }

    public void removeClient(Socket socket) {
        connectedClient.remove(getClient(socket));
        window.removeClient(socket);
    }

    public ArrayList<ClientInfo> getClientList() {
        return connectedClient;
    }

}
