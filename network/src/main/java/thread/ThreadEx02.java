package thread;

public class ThreadEx02 {

	public static void main(String[] args) {
		Thread thread01 = new DigitThread();
		Thread thread02 = new CharThread();
		
		thread01.start();
		thread02.start();
		
		return;
		//System.out.println("325325"); 메인스레드 return과 동시에 끝남 오류
	}

}
