package chapter03;

import mypackage.Goods2;

public class DiscountGoods2 extends Goods2 {
	private float discountrate = 0.5f;
	
	public float getDiscountPrice() {
		// protected는 자식에서 접근할 수 있다.
		return discountrate*price;
	}
}
