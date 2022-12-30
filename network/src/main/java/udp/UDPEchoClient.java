package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPEchoClient {
	public static final int PORT = 5000;
	private static final String SERVER_IP="127.0.0.1";
	public static final int Buffer_Size = 256;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner sc = null;
		try {
			socket = new DatagramSocket();
			sc = new Scanner(System.in);

			while (true) {
				//보내는부분
				System.out.print(">");
				String line = sc.nextLine();
				if("quie".equals(line)) {
					break;
				}
				byte[] sndData = line.getBytes("utf-8");
				DatagramPacket sndPacket = new DatagramPacket(sndData, sndData.length, new InetSocketAddress(SERVER_IP,5000));
				socket.send(sndPacket);

				//받는부분
				DatagramPacket rcvPacket = new DatagramPacket(new byte[Buffer_Size], Buffer_Size);
				socket.receive(rcvPacket);//블락킹

				byte[] rcvData = rcvPacket.getData();
				int offset = rcvPacket.getLength();
				String message = new String(rcvData, 0, offset, "utf-8");

				System.out.println("<" + message);
			}

		} catch (SocketException e) {
			System.out.println("[UDP Echo client] error: " + e);
		} catch (IOException e) {
			System.out.println("[UDP Echo client] error: " + e);
		} finally {
			if (sc != null) {
				sc.close();
			}

			if (socket != null && !socket.isClosed()) {
				socket.close();
			}

		}

	}

}
