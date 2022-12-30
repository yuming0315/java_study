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
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),true);
			
			while (true) {
				String request = br.readLine();
				if (request == null || "quit".equals(request)) {
					doQuit();
					break;
				}
				// 프로토콜 분석
				String[] tokens = request.split(":");
				switch(tokens[0]) {
					case "JOIN":
						doJoin(tokens[1]);
						break;
					case "SND":
						
						
				}
				
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void log(String str) {
		System.out.println("[client] " + str);
	}

	private void doJoin(String nickName) {
		this.nickname = nickName;
		String send = nickname + "님이 참여하였습니다.";
		broadCast(send, pw);
		synchronized (listWriters) {
			listWriters.add(pw);
		}
	}

	private void broadCast(String data, Writer writer) {
		for (Writer w : removeWriter()) {
			PrintWriter pw = (PrintWriter) w;
			pw.println(data);
		}
	}

	private void doQuit() {
		String send = nickname + "님이 퇴장 하였습니다.";
		log(send);
		broadCast(send, pw);
		this.listWriters = removeWriter();
	}

	private List<Writer> removeWriter() {
		List<Writer> list = null;
		synchronized (listWriters) {
			list = listWriters.stream().filter(cur -> cur != pw).collect(Collectors.toList());
		}
		return list;
	}

}
