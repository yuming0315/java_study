package prob3;

import java.util.Scanner;

public class Prob3 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while(true) {
			System.out.print("수를 입력 하세요 : ");

			int number = scanner.nextInt();
			int sum = 0;

			for(; number > 0; number-=2) {
				sum+=number;
			}

			System.out.println("결과값: " + sum);
		}
	}
}
