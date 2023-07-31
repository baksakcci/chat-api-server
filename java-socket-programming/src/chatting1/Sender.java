package chatting1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Sender implements Runnable{
    Socket socket;
    DataOutputStream dos;
    Scanner in;
    String info;
    public Sender(Socket socket) {
        this.socket = socket;
        this.in = new Scanner(System.in);
        try {
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.info = "[" + socket.getInetAddress() + ":" + socket.getPort() + "]";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (in != null) {
            try {
                System.out.print("메세지를 입력하세요 : ");
                dos.writeUTF(info + in.next());
                System.out.println("메세지를 성공적으로 보냈습니다.");
            } catch (IOException e) {
                System.out.println("메세지를 보내는 도중 에러가 발생했습니다.");
                e.printStackTrace();
            }
        }
    }
}
