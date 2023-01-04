package chat.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;
	
	public ChatWindow(String name) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		
		socket = new Socket();		
		try {
			socket.connect(new InetSocketAddress("127.0.0.1", 5000));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
		} catch (Exception e) {
			finish();
		}
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
//		buttonSend.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent actionEvent) {
//				sendMessage();
//			}
//		});
		// ActionEvent e를 받아와서 실행 람다식
		buttonSend.addActionListener((e) -> sendMessage());

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

//		textField.addKeyListener((e)-> {
//			char keyCode = e.getKeyChar();
//			if(keyCode == KeyEvent.VK_ENTER) {
//				sendMessage();
//			}
//		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});

		frame.setVisible(true);
		frame.requestFocus();
		frame.setAlwaysOnTop(true); 
		frame.pack();

		//IOStream 받아오기
		//ChatClientThread 생성하고 실행
		Join();
		new ChatClientThread().start();
		
	}

	private void finish() {
		// quit protocol 구현
		Quit();
		// exit java(application)
		System.exit(0);
	}

	private void sendMessage() {
		String text = textField.getText();
		textField.setText("");
		textField.requestFocus();
		
		if(text==null || "QUIT".equals(text)||"quit".equals(text)||"Quit".equals(text)) {
			finish();
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
			
		updateTextArea("ME:"+text);
	}

	private void updateTextArea(String message) {
		textArea.append(message+"\n");
	}
	private void Join() {
		sndMsg("JOIN:"+frame.getTitle());
	}
	
	private void Quit() {
		sndMsg("QUIT");
	}
	private void sndMsg(String str) {
		pw.println(str);
	}
	private void reName(String name) {
		sndMsg("RENAME:"+name);
		 frame.setTitle(name);
	}
	private boolean naming(String name) {
		if(name.matches(".*[^1-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣].*")) { //포함된걸 찾을 땐 이렇게
			updateTextArea("잘못된 별명 입력입니다. 특수문자는 입력할 수 없습니다 다시 입력해 주세요.");
			return false;
		}
		return true;
	}

	private class ChatClientThread extends Thread {

		@Override
		public void run() {
			try {
				while(true) {
					String data = br.readLine();
					
					if(data == null ) {
						updateTextArea("대화방과 연결이 끊겼습니다.");
						break;
					}
					else if("QUIT:OK".equals(data)) {
						updateTextArea("접속이 종료되었습니다.");
						break;
					}
					else if("ERROR".equals(data)) {
						updateTextArea("메시지 전달 오류");
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
		
		private void print(String str) {
			updateTextArea(str);
		}
		private void join(String...str ) {
			print(str[1]+"님이 입장하셨습니다.");
		}
		
		private void reName(String...str ) {
			print(str[2]+"님이 "+str[1]+"으로 별명을 변경하였습니다");
		}

		private void log(String str) {
			System.out.println("[server] "+str);
		}
	}
	
}
