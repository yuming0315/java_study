package thread;

public class UpperCaseCharRunnable extends UpperCaseChar implements Runnable{
	@Override
	public void run() {
		print();
	}
}
