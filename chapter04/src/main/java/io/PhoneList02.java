package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PhoneList02 {

	public static void main(String[] args) {
		Scanner scanner = null;
		try {
			File file = new File("phone.txt");
			if (!file.exists()) {
				System.out.println("file not found");
				return;
			}

			System.out.println("=================파일정보===================");
			System.out.println(file.getAbsolutePath());
			System.out.println(file.length());// 바이트단위로 나옴

			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));// long값으로
																													// 나옴

			System.out.println("=================전화번호===================");
			scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String name = scanner.next();
				String p1 = scanner.next();
				String p2 = scanner.next();
				String p3 = scanner.next();

				System.out.println(name + " : " + p1 + "-" + p2 + "-" + p3);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

	}

}
