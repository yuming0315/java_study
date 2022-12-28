package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		
		try {
			while(true) {
				Scanner sc = new Scanner(System.in);
				System.out.print(">");
				
				String s = sc.nextLine();
				if("exit".equals(s)) {
					break;
				}
				InetAddress[] inetAddresses = InetAddress.getAllByName(s);
				
				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostName()+" : "+inetAddress.getHostAddress());
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
