package keytyperserver;

import java.net.Socket;
import java.util.Scanner;

public class StreamHandler {

    private static StreamHandler instance = new StreamHandler();

    private StreamHandler() {
    }

    public static StreamHandler getInstance() {
        return instance;
    }

    public void getInputStream(Socket socket) {
        String text;
        try {
            Scanner in = new Scanner(socket.getInputStream());
            while (ServerHandler.getInstance().getClientList().contains(ServerHandler.getInstance().getClient(socket))) {
                if (socket.getInputStream().read() == -1) {
                    System.out.println("Client disconnected form socket " + socket.getInetAddress());
                    ServerHandler.getInstance().removeClient(socket);
                }

                if (in.hasNext()) {
                    text = in.nextLine();
                    System.out.println(text);
                    if (!socket.isClosed()) {
                        MacroHandler mh = new MacroHandler(text, KeyPresser.getInstance());
                        mh.process();
                    } else {
                        System.out.println("Client disconnected form socket " + socket.getInetAddress());
                        ServerHandler.getInstance().removeClient(socket);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
