
package keytyperserver;

import java.net.Socket;

public class ClientInfo implements Runnable {

    private boolean connecting;
    private final Socket socket;

    public ClientInfo(Socket socket) {
        this.socket = socket;
        this.connecting = true;
    }

    @Override
    public void run() {
        try {
            StreamHandler.getInstance().getInputStream(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkConnection() {
        return connecting;
    }

    public void terminateClientConnection() {
        this.connecting = false;
    }

    public Socket getSocket() {
        return this.socket;
    }
}
