package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	private static final String SERVER_IP = "127.0.0.1";
	
	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = null;
		try {
			socket = new Socket();

			socket.connect(new InetSocketAddress(SERVER_IP, EchoServer.PORT));
			log("connected");
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			while(true) {
				scanner = new Scanner(System.in);
				System.out.print(">");
				String data = scanner.nextLine();
				if(data == null || data.equals("exit")) {
					log("");
					break;
				}
				
				pw.println(data);
				String line = br.readLine();
				if(line == null) {
					log("closed by server");
					break;
				}
				
				System.out.println("<"+line);
			}
			
		} catch (IOException e) {
			System.out.println("[client] error" + e);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
				if(scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				log("error"+e);
			}
		}

	}
	private static void log(String message) {//메인에서 불러 쓸 거니까 스테틱
		System.out.println("[EchoClient] "+message);
	}

}
