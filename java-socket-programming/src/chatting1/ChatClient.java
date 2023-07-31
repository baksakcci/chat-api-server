package chatting1;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 7000);

            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket);

            Thread senderThread = new Thread(sender);
            Thread receiverThread = new Thread(receiver);

            senderThread.start();
            receiverThread.start();
        } catch (ConnectException ce) {
            System.out.println("연결에 문제가 발생했습니다. 다시 시도해주세요.");
            ce.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
