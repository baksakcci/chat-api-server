import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TcpSocketClient {
    public static void main(String[] args) {
        try {
            String serverIp = "127.0.0.1";

            Socket socket = new Socket(serverIp, 7000);
            System.out.println(serverIp + "로 연결중");

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);

            System.out.println("서버로부터 받은 메세지: " + dis.readUTF());

            System.out.println("연결 종료 시도");
            dis.close();
            socket.close();
            System.out.println("연결 종료 완료");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
