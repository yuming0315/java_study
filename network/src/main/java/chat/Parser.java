package chat;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Parser {
	// protocol에서는 암호풀린 문자열가지고 처리
	private String protocol(String msg) {
		String request = null;
		String[] tokens = msg.split(":");
		switch (tokens.length) {
		case 1:
			request = length1(msg);
			break;
		case 2:
			request = length2(msg);
			break;
		case 3:
			request = length3(msg);
			break;
		}
		return request;
	}

	private String length1(String msg) {
		String request = null;
		String[] tokens = msg.split(" ");
		switch (tokens.length) {
		case 2:
			if ("RENAME".equals(tokens[0])) {
				request = msg.replace(" ", ":");
			}
			break;
		default:
			if ("QUIT".equals(tokens[0])) {
				request = tokens[0];
			} 
			else { // snd msg 일 경우
				request = new String(encoding(msg));
//				request = new String(encoding(msg),StandardCharsets.UTF_8);
			}
		}
		return request;
	}

	private String length2(String str) {

	}

	private String length3(String str) {

	}

	public byte[] msg(String str) {
		return encoding(protocol(str));
	}

	public String msg(byte[] str) {
		return protocol(decoding(str));
	}

	private byte[] encoding(String str) {
		return Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
	}

	private String decoding(byte[] str) {
		return new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
	}

}
