package chat.gui;
import java.util.Scanner;

public class ChatClientApp {

	public static void main(String[] args) {
		String name = null;
		Scanner sc = new Scanner(System.in);

		while(name==null || "".equals(name) || !naming(name)) {
			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = sc.nextLine();
		}
		sc.close();

		new ChatWindow(name).show();
	}

	private static boolean naming(String name) {
		if(name.matches(".*[^1-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣].*")) { //포함된걸 찾을 땐 이렇게
			System.out.println("잘못된 대화명 입력입니다. 특수문자는 입력할 수 없습니다 다시 입력해 주세요.");
			return false;
		}
		return true;
	}
}
