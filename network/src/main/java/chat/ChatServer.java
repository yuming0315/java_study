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
		try {
			ServerSocket serverSocket = new ServerSocket();
			
//			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress("0.0.0.0",PORT));
			
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket).start();
			}
			
		} catch (IOException e) {
			log("IOException " + e);
		}
	}

	private static void log(String err) {
		System.out.println("[Server]"+err);
	}
}
