package chatting2.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {

    public static void main(String[] args) {
        new MultiChatClient().start();
    }
    public void start() {
        Scanner in = new Scanner(System.in);
        System.out.print("사용자 명을 입력하세요 : ");
        String username = in.next();

        try {
            Socket socket = new Socket("127.0.0.1", 7000);
            System.out.println("서버에 연결되었습니다.");

            Thread senderThread = new Thread(new ClientSender(socket, username));
            Thread receiverThread = new Thread(new ClientReceiver(socket));

            senderThread.start();
            receiverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
