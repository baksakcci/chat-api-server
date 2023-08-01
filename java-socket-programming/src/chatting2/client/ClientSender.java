package chatting2.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientSender implements Runnable{

    String username;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public ClientSender(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        try {
            dos.writeUTF(username);

            while (dos != null) {
                dos.writeUTF("[" + username + "] " + in.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
