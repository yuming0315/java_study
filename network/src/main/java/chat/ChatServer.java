package chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static final int PORT = 5000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();
			
			serverSocket.bind(new InetSocketAddress("0.0.0.0",PORT));
			System.out.println("서버 on");
			
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket).start();
			}
			
		} catch (IOException e) {
			log("서버 실행 IOException " + e);
		}finally {
			if(serverSocket != null || !serverSocket.isClosed() ) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					log("서버소켓 close 오류"+e);
				}
			}
		}
	}

	private static void log(String err) {
		System.out.println("[Server]"+err);
	}
}
