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
			updateTextArea("비정상 종료입니다.");
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
		frame.setAlwaysOnTop(true); 
		frame.pack();
		textField.requestFocus();

		//IOStream 받아오기
		//ChatClientThread 생성하고 실행
		
		
		Join();
		new ChatClientThread().start();
		
	}

	private void finish() {
		// quit protocol 구현
		if(!socket.isClosed()||socket!=null) {
			Quit();
			try {
				socket.close();
			} catch (Exception e) {
				updateTextArea("소켓 close 오류");
			}
		}
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

	private class ChatClientThread extends chat.ChatClientThread {

		public ChatClientThread() {
			super(br);
		}
		
		@Override
		protected void print(String str) {
			updateTextArea(str);
		}
		
		@Override
		protected void log(String str) {
			updateTextArea("[server] "+str);
			 
		}
		
	}
	
}
