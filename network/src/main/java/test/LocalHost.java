package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			String hostName = inetAddress.getHostName();
			String hostIpAddress = inetAddress.getHostAddress();
			
			System.out.println(hostName);
			System.out.println(hostIpAddress);
			
			byte[] IpAddresses = inetAddress.getAddress();
			for(byte IpAddress:IpAddresses) {
				System.out.print(IpAddress & 0x000000ff); //and연산 해줘야 그대로 된 숫자나옴
				System.out.print(".");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
