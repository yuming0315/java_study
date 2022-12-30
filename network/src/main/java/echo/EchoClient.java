package echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		try {
			int a = System.in.read();
			bw.write(a);
			bw.write("\n");
			bw.flush();
			
			
			System.out.println(" 12421" );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			socket = new Socket();

			socket.connect(new InetSocketAddress(SERVER_IP, EchoServer.PORT));
			log("connected");
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			scanner = new Scanner(System.in);
			while(true) {
				System.out.print(">");
				String line = scanner.nextLine();
				
				if("exit".equals(line)) {
					break;
				}
				
				pw.println(line);
				String data = br.readLine();
				if(data == null) {
					log("closed by server");
					break;
				}
				
				System.out.println("<" + data);
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
