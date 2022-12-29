package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoRequestHandler extends Thread {
	private Socket socket;
	
	public EchoRequestHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		log("connected by client[port:" +remotePort + "]");
		
		try {				
			//flash를 자동으로 해라는 의미로 true 넣어줌
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			//여과기를 많이 거쳐서 상단 작업코드에서 불필요한 코드를 줄임
			
			while(true) {
				String data = br.readLine();
				if(data == null || data.equals("exit")) {
					log("closed by client");
					break;
				}
				log("received:" + data);
				pw.println("[server] : "+data);
			}
			
		} catch(IOException ex) {
			log("error:" + ex);
		} finally {
			try {
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch(IOException ex) {
				log("error:" + ex);
			}
		}
	}
	
	private void log(String message) {//메인에서 불러 쓸 거니까 스테틱
		System.out.println("[EchoServer#"+getId()+message+"]");
	}
}
