import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TcpSocketThreadServer implements Runnable{
    public ServerSocket serverSocket;
    public Thread[] thr_arr;
    public static void main(String[] args) {
        TcpSocketThreadServer tcpSocketThreadServer = new TcpSocketThreadServer();
        tcpSocketThreadServer.start(5);
    }

    public TcpSocketThreadServer() {
        System.out.println("Socket Server 생성중");
        try {
            serverSocket = new ServerSocket(7000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("생성 완료");
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("연결 시도");
                serverSocket.setSoTimeout(10000);
                Socket socket = serverSocket.accept();
                System.out.println("연결 완료! 소켓 생성");
                System.out.println("상대방 IP 주소: " + socket.getInetAddress());

                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);

                dos.writeUTF("안녕하세요!!");
                System.out.println("메세지 전송 완료");

                dos.close();
                socket.close();
            } catch (SocketTimeoutException e) {
                System.out.println("10초간 연결되지 않았습니다.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(int n) {
        thr_arr = new Thread[n];
        for (int i = 0; i < n; i++) {
            thr_arr[i] = new Thread(this);
            thr_arr[i].start();
        }
    }
}

