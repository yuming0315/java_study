package chat.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;

	public ChatWindow(String name) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				sendMessage();
			}
		});
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
				// clean-up

				// exit java(Application)
				System.exit(0);
			}
		});

		frame.setVisible(true);
//		frame.requestFocus();
		frame.pack();

		//IOStream 받아오기
		//ChatClientThread 생성하고 실행
		
	}

	private void finish() {
		// quit protocol 구현

		// exit java(application)
		System.exit(0);
	}

	private void sendMessage() {
		String message = textField.getText();
		System.out.println(message);
		message.replaceAll("\n", "");
		updateTextArea(message);
//		textArea.setText(textArea.getText() + message);
		textField.setText("");
		textField.requestFocus();
	}

	private void updateTextArea(String message) {
		textArea.append(message+"\n");
	}

	private class ChatClientThread extends Thread {

		@Override
		public void run() {
			updateTextArea("1234");
		}
	}
}
