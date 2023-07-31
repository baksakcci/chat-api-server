package chatting1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ChatServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(7000);
            System.out.println("tcp socket 연결 시도");

            socket = serverSocket.accept();
            System.out.println("연결 완료");

            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket);

            Thread senderThread = new Thread(sender);
            Thread receiverThread = new Thread(receiver);

            senderThread.start();
            receiverThread.start();
        } catch (SocketException e) {
            e.printStackTrace();
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}