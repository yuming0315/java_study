package thread;

public class ThreadEx01 {

	public static void main(String[] args) {
		//스레드의 제어가 어려움
		new DigitThread().start();
		new CharThread().start();
		// DigitThread , CharThread , main 현재 Thread 3개임.
		
		
		try {
			Thread.sleep(10000);	//메인도 스레드
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("끝");
	}

}
