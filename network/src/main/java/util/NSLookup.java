package util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {

	public static void main(String[] args) {
		try {
			InetAddress[] inetAddresses = InetAddress.getAllByName("www.douzone.com");
			
			//구현하기 과제
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
