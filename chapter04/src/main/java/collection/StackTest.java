package collection;

import java.util.Stack;

public class StackTest {

	public static void main(String[] args) {
		Stack<String> s = new Stack<>();
		
		s.push("둘리");
		s.push("마이콜");
		s.push("도우너");
		
		while(!s.isEmpty()) {
			String str = s.pop();
			System.out.println(str);
		}
		
		//비어있는 경우 예외처리
//		s.pop();
		
		s.push("둘리");
		s.push("마이콜");
		s.push("도우너");
		
		System.out.println(s.pop());
		System.out.println(s.peek());	//맨위의값 꺼내진 않고 보는거
		System.out.println(s.pop());

	}

}
