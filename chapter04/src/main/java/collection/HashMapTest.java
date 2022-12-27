package collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapTest {

	public static void main(String[] args) {
		Map<String,Integer> m = new HashMap<>();
		
		m.put("one", 1);	//auto boxing 1 < Integer
		m.put("two", 2);
		m.put("three", 3);
		
		int i = m.get("one");	//auto unboxing
		int j = m.get(new String("three"));	//자료구조는 값으로 대칭
		
		System.out.println(i + " "+ j);
		
		m.put("three", 30);
		System.out.println(m.get(new String("three")));	//키 중복 시 현재 있는 키의 값을 변경함
		
		//map은 iterrator이 없어 순회불가능, 키를 배열로 받아와서 순회하는듯?
		Set<String> str = m.keySet();
		for(String k : str) {
			System.out.println(m.get(k));
		}
		//트리는 직접 만드는거 링크드리스트로 하면 됨
		
	}

}
