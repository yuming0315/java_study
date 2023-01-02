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
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			while (true) {
				String request = br.readLine();
				if (request == null || "QUIT".equals(request)) {
					doQuit(request);
					break;
				}
				// 프로토콜 분석
				String[] tokens = request.split(":");
				switch (tokens[0]) {
				case "JOIN":
					doJoin(request,tokens[1]);
					break;
				case "SND":
					sendMsg(request + ":" + nickname);
					break;
				case "RENAME":
					reName(request, tokens[1]);
					break;

				default:
					errorProtocol();
					break;

				}

			}

		} catch (IOException e) {
			// 일단 나갔다는출력
			doQuit();
			log(nickname+"비정상 종료");
		} finally {
			// 소켓닫음 소켓에서 받아온거라 알아서 다 닫힘
			if (socket != null || !socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("socket close Exception" + e);
				}
			}
		}

	}

	private void log(String str) {
		System.out.println("[Server] " + str);
	}

	private void errorProtocol() {
		pw.println("ERROR");
	}

	private void doJoin(String req,String nickName) {
		this.nickname = nickName;
		log(req);
		broadCast(req);
		synchronized (listWriters) {
			listWriters.add(pw);
		}
		Join(req + ":OK");
	}

	private void doQuit() {
		doQuit("QUIT");
	}

	private void doQuit(String msg) {
		Quit();
		this.listWriters = removeWriter();
		String send = msg+":"+nickname;
		log(send);
		broadCast(send);
	}

	private void broadCast(String data) {
		for (Writer w : removeWriter()) {
			PrintWriter write = (PrintWriter) w;
			write.println(data);
		}
//		sendME("SND:OK");
	}

	private void sendME(String str) {
		pw.println(str);
	}

	private void Join(String msg) {
		sendME(msg);
	}

	private void Quit() {
		sendME("QUIT:OK");
	}

	private void reName(String msg, String name) {
		String send = msg + ":" + nickname;
		sendME(send);
		broadCast(send);
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
		broadCast(str);
	}
}
