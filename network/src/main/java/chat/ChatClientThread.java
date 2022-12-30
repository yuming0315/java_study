package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;

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
				if(data == null || data.equals("quit")) {
					log("closed by client");
					break;
				}
				System.out.println(data);
			}
		}catch(IOException ex) {
			log("error:" + ex);
		}
		
	}


	private void log(String str) {
		System.out.println("[server] "+str);
	}
}
