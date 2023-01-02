package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatServerThread extends Thread {
	private static List<Writer> listWriters = new ArrayList<Writer>();
	private String nickname;
	private Socket socket;
	private PrintWriter pw;

	public ChatServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			while (true) {
				String request = br.readLine();
				if (request == null || "quit".equals(request)) {
					doQuit();
					break;
				}
				// 프로토콜 분석
				String[] tokens = request.split(":");
				switch (tokens[0]) {
				case "JOIN":
					doJoin(request);
					break;
				case "SND":
					sendMsg(request+":"+nickname);
					break;
				case "RENAME":
					reName(tokens[1]);
					break;
					

				}

			}

		} catch (IOException e) {
			//일단 나갔다는출력
			doQuit();
		}finally {
			//소켓닫음 소켓에서 받아온거라 알아서 다 닫힘
			if(socket!=null||!socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("socket close Exception"+e);
				}
			}
		}

	}

	private void log(String str) {
		System.out.println("[Server] " + str);
	}

	private void doJoin(String nickName) {
		this.nickname = nickName;
		String send = "JOIN:"+nickName;
		log(send);
		broadCast(send);
		synchronized (listWriters) {
			listWriters.add(pw);
		}
		Join();
		
//		String send = nickname + "님이 참여하였습니다.";
	}
	
	private void doQuit() {
		Quit();
		this.listWriters = removeWriter();
		String send = nickname + "님이 퇴장 하였습니다.";
		log(send);
		broadCast(send);
	}
	
	private void broadCast(String data) {
		for (Writer w : removeWriter()) {
			PrintWriter write = (PrintWriter) w;
			write.println(data);
		}
//		pw.println("SND:OK");
	}
	private void sendME(String str) {
		pw.println(str);
	}
	private void Join() {
		sendME(nickname+"님 환영합니다.");
	}

	private void Quit() {
		sendME("방에서 퇴장하였습니다.");
	}
	
	private void reName(String name) {
		sendME(nickname +"에서"+name+"으로 별명을 변경하였습니다.");
		broadCast(nickname +"님이 "+name+"으로 별명을 변경하셨습니다.");
		nickname = name;
	}

	private List<Writer> removeWriter() {
		List<Writer> list = null;
		synchronized (listWriters) {
			list = listWriters.stream().filter(cur -> cur != pw).collect(Collectors.toList());
		}
		return list;
	}

	private void sendMsg(String str) {
		broadCast(nickname + ":" + str);
	}
}
