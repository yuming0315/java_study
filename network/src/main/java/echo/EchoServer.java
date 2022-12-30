package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static final int PORT = 8000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();
			
			serverSocket.bind(new InetSocketAddress("0.0.0.0",PORT));//telnet 192.0.0.1 5000 으로 접근가능
			log("starts...[port:" +PORT + "]");
			
			while(true) {
				Socket socket = serverSocket.accept();
				new EchoRequestHandler(socket).start();
			}
			
		} catch (IOException e) {
			log("error:" + e);
		} finally {
			try {
				if(serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	private static void log(String message) {//메인에서 불러 쓸 거니까 스테틱
		System.out.println("[EchoServer#" +Thread.currentThread().getId()+"] "+message);
	}

}
