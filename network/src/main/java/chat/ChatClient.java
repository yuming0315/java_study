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
	private static PrintWriter pw;

	public static void main(String[] args) {
		Scanner sc = null;
		Socket socket = null;
		
		try {
			sc = new Scanner(System.in);
			socket = new Socket();
			
			socket.connect(new InetSocketAddress(SERVER_IP, PORT));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			
			while(true) {
				System.out.print("사용하실 별명을 입력해 주세요>>");
				String name = sc.nextLine();
				if(name==null || "".equals(name)||name.matches("[^1-9a-zA-Z가-힣]")) {
					log("잘못된 별명 입력입니다. 특수문자는 입력할 수 없습니다 다시 입력해 주세요.");
					continue;
				}
				else {
					pw.println("JOIN:"+name);
					break;
				}
			}
						
			new ChatClientThread(new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"))).start();
			Thread.sleep(10);
			
			while(!socket.isClosed()) {

				String text = sc.nextLine();
				System.out.print(">>");
				String[] tokens = text.split(" ");
				switch(tokens[0]) {
				case "RENAME":
				case "rename":
					reName(tokens[1]);
					break;
				
				default:
					sndMsg("SND:"+text);
				}
				
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
				log("socket close error"+e);
			}
		}
	}
	private static void sndMsg(String str) {
		pw.println(str);
	}
	private static void reName(String name) {
		sndMsg("RENAME:"+name);
	}
	
	private static void log(String message) {//메인에서 불러 쓸 거니까 스테틱
		System.out.println("[Client Error] "+message);
	}

}
