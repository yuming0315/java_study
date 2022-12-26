package prob01;

import java.util.Scanner;

public class Prob01 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		final int[] MONEYS = {50000, 10000, 5000, 1000, 500, 100, 50, 10, 5, 1};

		System.out.print("금액:");
		int money = scanner.nextInt();

		for(int i = 0; i < MONEYS.length&&money>0; i++) {
			int q = money/MONEYS[i];
			if(q>0) {
				System.out.println(MONEYS[i]+"원 : "+q+"개");
				money%=MONEYS[i];
			}
		}

		scanner.close();
	}
}
