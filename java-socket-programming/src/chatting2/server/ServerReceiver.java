package chatting2.server;

import static chatting2.server.MultiChatServer.clients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerReceiver implements Runnable{
    private final Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ServerReceiver(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String username = "";
        try {
            username = dis.readUTF();
            sendToAll("[운영자] " + username + "님이 채팅방에 입장하셨습니다.");
            clients.put(username, dos);
            System.out.println("[운영자] 현재 서버 접속자 수는 " + clients.size() + "입니다.");

            while (dis != null) {
                sendToAll(dis.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sendToAll("[운영자] " + username + "님께서 채팅방을 나가셨습니다.");
            clients.remove(username);
            System.out.println("[운영자] " + "[" + socket.getInetAddress() + ":" + socket.getPort() + "]이 채팅방을 나갔습니다.");
            System.out.println("[운영자] 현재 서버 접속자 수는 " + clients.size() + "입니다.");
        }
    }

    public static void sendToAll(String m) {
        for(String name : clients.keySet()) {
            try {
                clients.get(name).writeUTF(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
