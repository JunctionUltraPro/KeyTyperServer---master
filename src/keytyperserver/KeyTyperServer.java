package keytyperserver;

import java.net.UnknownHostException;

public class KeyTyperServer {

    public static void main(String[] args) throws UnknownHostException {
        ServerHandler.getInstance().start();
    }

}
