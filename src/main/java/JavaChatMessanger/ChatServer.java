package JavaChatMessanger;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by ZuZ on 2016-08-15.
 */
public class ChatServer {
    //allows to create many clients and many logins
    static Vector ClientSockets;
    static Vector LoginNames;

    ChatServer() throws IOException { //create constructor
        ServerSocket server = new ServerSocket(5220);
        //initialize variables
        ClientSockets = new Vector();
        LoginNames = new Vector();

        while (true) {
            Socket client = server.accept();
            AcceptClient acceptClient = new AcceptClient(client);
        }
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
    }

}
