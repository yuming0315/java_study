package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class PhoneList01 {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			File file = new File("phone.txt");
			if(!file.exists()) {
				System.out.println("file not found");
				return;
			}
			
			System.out.println("=================파일정보===================");
			System.out.println(file.getAbsolutePath());
			System.out.println(file.length());// 바이트단위로 나옴

			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));// long값으로
																													// 나옴

			System.out.println("=================전화번호===================");
			// 1. 기반스트림(FileInputStream)
			FileInputStream fis = new FileInputStream(file);

			// 2. 보조스트림1(byte|byte|byte -> char)
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");

			// 3. 보조스트림2(char|char|....|\n -> "charcharchar")
			br = new BufferedReader(isr);
			
			// 4. 처리
			String line = null;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line,"\t ");
				
				int idx = 0;
				while(st.hasMoreElements()) {
					String token = st.nextToken();
					
					if(idx == 0) {
						System.out.print(token + " : ");
					}else if(idx == 1) {
						System.out.print(token + " - ");
					}else if(idx == 2) {
						System.out.print(token + " - ");
					}else {
						System.out.println(token);
					}
					idx++;
				}
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("Error: " + e);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
