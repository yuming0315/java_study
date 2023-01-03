package chat;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Test {

	public static void main(String[] args) {
		Parser p = new Parser();
		
		OutputStream os = null;
		
		OutputStreamWriter o = null;
		
		
		System.out.println("QUIT".split(" ").length);
		System.out.println("QUIT".split(" ")[0]);
		
//		byte[] b = p.msg("1234567");
//		System.out.println(new String(b));
//		System.out.println(p.msg(b));

	}

}
