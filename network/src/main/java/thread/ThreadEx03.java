package thread;

public class ThreadEx03 {

	public static void main(String[] args) {
		new UpperCaseChar().print();
		//어떤 클레스에 있는 함수를 스레드에 태우고 싶을 때 Runnable
		
		Thread thread01 = new DigitThread();
		Thread thread02 = new CharThread();
		Runnable r = new Thread(new UpperCaseCharRunnable());
		//인터페이스로도 접근 가능한듯?
		//원래는 Runnable 말고 Thread로 받는게 예제였음
		//run만 가능하고 스레드는 아님
		
		thread01.start();
		thread02.start();
		r.run();
		//Thread보다 Runnable이 먼저 드가지는거같음
	}

}
