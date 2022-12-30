package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "127.0.0.1";
	private static final int PORT = 5000;

	public static void main(String[] args) {
		Scanner sc = null;
		Socket socket = null;
		
		try {
			sc = new Scanner(System.in);
			socket = new Socket();
			
			socket.connect(new InetSocketAddress(SERVER_IP, PORT));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			
			System.out.print("사용하실 별명을 입력해 주세요>");
			String name = sc.nextLine();
			pw.println("JOIN:"+name);
			
			new ChatClientThread(new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"))).start();
			Thread.sleep(10);
			
			
			//명령어> 방식으로 할거면 입력받을때마다 바꿔서 해야함 switch로 케이스 나누기
			while(true) {
				System.out.print("SEND"+">");
				String text = sc.nextLine();
				pw.println("SND:"+text);
			}
			
		}catch (IOException e) {
			System.out.println("IOException" + e);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException" + e);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
				if(sc != null) {
					sc.close();
				}
			} catch (IOException e) {
				log("error"+e);
			}
		}
	}
	private static void log(String message) {//메인에서 불러 쓸 거니까 스테틱
		System.out.println("[Client Error] "+message);
	}

}
