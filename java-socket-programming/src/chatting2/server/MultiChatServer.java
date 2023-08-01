package chatting2.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;

public class MultiChatServer {
    public static HashMap<String, DataOutputStream> clients;

    public MultiChatServer() {
        clients = new HashMap<>();
        Collections.synchronizedMap(clients); // 동기화
    }

    public static void main(String[] args) {
        MultiChatServer multiChatServer = new MultiChatServer();
        multiChatServer.start();
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(7000);
            System.out.println("서버 시작");

            while (true) {
                socket = serverSocket.accept();
                System.out.println("[운영자] " + "[" + socket.getInetAddress() + ":" + socket.getPort() + "]이 채팅방에 입장하셨습니다.");
                Thread thread = new Thread(new ServerReceiver(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
