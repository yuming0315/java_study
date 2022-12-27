package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class FileReaderTest {

	public static void main(String[] args) {
		Reader in = null;
		InputStream is = null;
		
		try {
			in = new FileReader("test.txt");
			
			int count = 0;
			int data = -1;
			while((data=in.read())!=-1) {
				System.out.print((char)data);
				count++;
			}
			System.out.println("\ncount:"+count);
			System.out.println("==============================================");
			
			is = new FileInputStream("test.txt");
			count = 0;
			data = -1;
			while((data=is.read())!=-1) {
				System.out.print((char)data);
				count++;
				
			}
			System.out.println("\ncount:"+count);
			//
			
		} catch (FileNotFoundException e) {	
			System.out.println("File Not Found:"+e);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {		
			try{
				if(in!=null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
				
		}

}

}
