package io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedOutputStreamTest {

	public static void main(String[] args) {
		BufferedOutputStream bos = null;	//얘만 정리해도 자원정리 알아서 함
		
		try {
			//기반 스트림
			FileOutputStream fos = new FileOutputStream("hello.txt");
			
			//보조 스트림
			bos = new BufferedOutputStream(fos);
			
			for(int i='a';i<='z';i++) {
				bos.write(i);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(bos != null) {
					bos.close();	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
