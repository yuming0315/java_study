package test;

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
			
			
			Socket socket = serverSocket.accept(); // blocking
			
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			log("connected by client[port:" +PORT + "]");
			
			try {				
				//flash를 자동으로 해라는 의미로 true 넣어줌
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//여과기를 많이 거쳐서 상단 작업코드에서 불필요한 코드를 줄임
				
				while(true) {
					pw.print("[client] : ");
					pw.flush();
					String data = br.readLine();
					if(data == null || data.equals("exit")) {
						log("closed by client");
						break;
					}
					log("received:" + data);
					pw.println("[server] : "+data);
				}
				
			} catch(IOException ex) {
				log("error:" + ex);
			} finally {
				try {
					if(socket != null && !socket.isClosed()) {
						socket.close();
					}
				} catch(IOException ex) {
					log("error:" + ex);
				}
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
		System.out.println("[EchoServer] "+message);
	}

}
