package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class ChatClientThread extends Thread {
	private BufferedReader br;
	
	public ChatClientThread(BufferedReader br) {
		this.br =br; 
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				String data = br.readLine();
				
				if(data == null ) {
					log("대화방과 연결이 끊겼습니다.");
					break;
				}
				else if("QUIT:OK".equals(data)) {
					log("접속이 종료되었습니다.");
					break;
				}
				else if("ERROR".equals(data)) {
					log("메시지 전달 오류");
					continue;
				}
				
				String[] tokens = data.split(":");
				
				switch(tokens[0]) {
				case "SND":
					print(tokens[tokens.length-1]+":"+addString(Arrays.copyOfRange(tokens,1,tokens.length-1)));
					break;
					
				case "RENAME":
					if(isMsg(tokens)) {
						reName(tokens);
					}
					else {
						reNameMe(tokens);
					}
					break;
				
				case "JOIN":
					if(isMsg(tokens)) {
						join(tokens);
					}
					else {
						joinMe(tokens);
					}
					break;
				case "QUIT":
					Quit(tokens[1]);
					break;
				}
				
			}
		}catch(IOException ex) {
			log("서버와 연결이 끊겼습니다.");
		}finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					log("buffer close error");
				}
			}
		}
		
	}
	private String addString(String[] str) {
		String text="";
		for(String s : str) {
			text+=s;
		}
		return text;
	}
	private void reNameMe(String[] str) {
		print(str[1]+"로 별명이 변경되었습니다.");
	}
	private void Quit(String str) {
		print(str+"님이 퇴장하셨습니다.");
	}
	
	private void joinMe(String[] str) {
		print(str[1]+"님 환영합니다.");
	}

	private boolean isMsg(String[] str) {
		return !str[str.length-1].equals("OK");
	}
	
	protected void print(String str) {
		System.out.print(str+"\n>>");
	}
	private void join(String...str ) {
		print(str[1]+"님이 입장하셨습니다.");
	}
	
	private void reName(String...str ) {
		print(str[2]+"님이 "+str[1]+"으로 별명을 변경하였습니다");
	}

	protected void log(String str) {
		System.out.println("[server] "+str);
	}
}
