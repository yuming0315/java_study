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
				if(name==null || "".equals(name) || !naming(name)) {
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
				if(text==null || "QUIT".equals(text)||"quit".equals(text)||"Quit".equals(text)) {
					break;
				}
				
				String[] tokens = text.split(" ");
				switch(tokens[0]) {					
				case "RENAME":
				case "rename":
					if(naming(tokens[1])) {
						reName(tokens[1]);
					}
					break;			
				default:
					sndMsg("SND:"+text);
				}
				
			}
			
		}
		catch(Exception e){
			log("비정상 종료");
		} finally {
			Quit();
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
	private static void Quit() {
		sndMsg("QUIT");
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

	private static boolean naming(String name) {
		if(name.matches(".*[^1-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣].*")) { //포함된걸 찾을 땐 이렇게
			log("잘못된 별명 입력입니다. 특수문자는 입력할 수 없습니다 다시 입력해 주세요.");
			return false;
		}
		return true;
	}
}
