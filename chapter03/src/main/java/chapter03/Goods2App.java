package chapter03;

import mypackage.Goods2;

public class Goods2App {

	public static void main(String[] args) {
		Goods2 g = new Goods2();
		
		//  public 접근 제한이 없다.
		g.name = "camera";
		
		// protected는 같은 패키지에서 접근이 가능하다.
		// 더 중요한 것은 자식에서 접근이 가능하다.
		// g.price = 10000;
		
		// 디폴트는 같은 패키지에서 접근이 가능하다.
		// g.countStock = 10;
		
		// private는 같은 클래스에서만 접근이 가능하다.
		// g.countSold = 50;

	}

}
