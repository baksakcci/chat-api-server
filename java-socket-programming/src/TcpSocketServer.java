import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpSocketServer {

    public static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }

    public static void main(String[] args) {
        // server init
        ServerSocket serverSocket = null;
        System.out.println(getTime() + "서버 준비 완료");
        try {
            serverSocket = new ServerSocket(7000);
            System.out.println();
        } catch(IOException e) {
            e.printStackTrace();
        }

        // communicate
        while (true) {
            try {
                System.out.println(getTime() + "연결 요청 대기");

                serverSocket.setSoTimeout(5000); // 5초 안에 연결 안할 시 SocketTimeoutException
                Socket socket = serverSocket.accept();

                System.out.println(getTime() + socket.getInetAddress() + "로부터 연결요청 들어왔음.");

                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputStream);

                dos.writeUTF("Test Message");
                System.out.println(getTime() + "데이터 전송 완료");

                dos.close();
                socket.close();

            } catch (SocketTimeoutException e) {
                System.out.println("5초동안 연결요청이 없어 종료");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}