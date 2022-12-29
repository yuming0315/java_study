package thread;

public class UpperCaseChar {
	public void print() {
		for(char c = 'A';c<='Z';c++){
			System.out.print(c);
			try {
				Thread.sleep(100);//단독사용 가능 static 함수
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
